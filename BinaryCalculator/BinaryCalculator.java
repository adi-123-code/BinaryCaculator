
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class BinaryCalculator {
    /**
     * @author adi
     * CF (Carry Flag)进位标志位。加(减)法运算时，若最高位有进(借)位则CF=1
     * OF (Overflow Flag)溢出标志位。当算术运算的结果超出了有符号数的可表达范围时，OF=1
     * ZF (Zero Flag)零标志位。当运算结果为零时ZF=1
     * SF (Sign Flag)符号标志位。当运算结果的最高位为1时，SF=1
     * PF(Parity Flag)奇偶标志位。运算结果的低8位中“1”的个数为偶数时，PF=1
     * AF (Auxiliary Carry Flag) 辅助进位标志位。加(减)操作中，若 Bit3向 Bit4有进位(借位)，
     */

    private JPanel  panel;
    private JButton button_1;
    private JButton button_0;
    private JButton equal_button;
    private JButton add_button;
    private JButton minus_button;
    private JButton NOTButton;
    private JButton ORButton;
    private JButton ANDButton;
    private JButton XORButton;
    private JButton backspaceButton;
    private JButton CLRButton;
    private JButton COMPButton;
    private JEditorPane editorPane1;
    private JEditorPane editorPane2;
    private JEditorPane editorPane3;
    private JRadioButton JRadioButton1;
    private JRadioButton JRadioButton2;
    private JRadioButton JRadioButton3;


    private static int carry_flag = 0;//进位标志位
    private static int overflow_flag = 0;//溢出标志位
    private static int zero_flag = 0;//零标志位
    private static int sign_flag = 0;//符号标志位
    private static int parity_flag = 0;//奇偶标志位
    private static int auxiliary_carry_flag = 0;//辅助进位标志位
    private boolean flag = false;//实现连加、连减标志位
    private boolean clr1_flag = false;//输入操作数标志位
    private String currentOperation = "+";//当前运算符
    private String currentResult = "0";//当前运算结果
    private static int currentWordLen = 8;

    public BinaryCalculator() {
        button_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

             if (flag) {
                editorPane1.setText("");
                flag = false;
            }
            if (clr1_flag) {
                editorPane1.setText("");
                clr1_flag = false;
            }
            String temp = editorPane1.getText();
            temp = temp + "1";
            editorPane1.setText(temp);
            int len = editorPane1.getText().length();
            if(len > currentWordLen)
            {
                String t = editorPane1.getText();
                t = t.substring(0,t.length()-1);
                editorPane1.setText(t);
                JOptionPane.showMessageDialog(null, "非法错误输入:超出字长范围", "非法错误",JOptionPane.ERROR_MESSAGE);
            }
            }
        });

        button_0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (flag) {
                    editorPane1.setText("");
                    flag = false;
                }
                if (clr1_flag) {
                    editorPane1.setText("");
                    clr1_flag = false;
                }
                String temp = editorPane1.getText();
                temp = temp + "0";
                editorPane1.setText(temp);
                int len = editorPane1.getText().length();
                if(len > currentWordLen)
                {
                    String t = editorPane1.getText();
                    t = t.substring(0,t.length()-1);
                    editorPane1.setText(t);
                    JOptionPane.showMessageDialog(null, "非法错误输入:超出字长范围", "非法错误",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        backspaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String temp = editorPane1.getText();
                if(!temp.equals("")){
                    temp = temp.substring(0,temp.length()-1);
                    editorPane1.setText(temp);
                }

            }
        });

        CLRButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //如果本次运算完整地完成，两个文本框全部清除
                if(!editorPane2.getText().equals("")&&editorPane2.getText().substring(editorPane2.getText().length()-1).equals("=")||editorPane1.getText().equals("")||!editorPane2.getText().equals("")&&editorPane2.getText().substring(editorPane2.getText().length()-1).equals(")"))
                {
                    editorPane1.setText("");
                    editorPane2.setText("");
                    editorPane3.setText("");
                    currentResult = "0";
                    currentOperation = "+";
                    flag = false;
                    clr1_flag = false;
                }
                //如果本次运算未完成，仅仅需要清除当前输入的操作数
                else{
                    editorPane1.setText("");
                }

            }
        });

        add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(editorPane1.getText().length()<currentWordLen)
                {
                    Object[] options = { "OK", "CANCEL" };
                    int n = JOptionPane.showOptionDialog(panel, "The length of the input binary number is not long enough\nClick OK to continue", "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                }
                if(!editorPane2.getText().equals("") && editorPane2.getText().substring(editorPane2.getText().length()-1).equals("=")){
                    currentResult=editorPane1.getText();
                    editorPane2.setText(editorPane1.getText() + "+");
                    currentOperation = "+";
                    clr1_flag = true;
                }
                else
                {
                    String temp = editorPane2.getText();
                    if(flag){
                        temp = temp.substring(0,temp.length()-1) + "+";
                    }
                    else{
                        if(!currentOperation.equals("NOT") && !currentOperation.equals("COMP") )
                        {
                            //无需按等号，直接输出结果
                            temp = temp + editorPane1.getText() + "+" ;
                            String temp1 = editorPane1.getText();
                            String temp2 = currentResult;
                            String result = add(temp1 , temp2);
                            if(!editorPane1.getText().equals(""))
                                editorPane1.setText(result);
                        }
                        else temp = temp +  "+" ;
                    }
                    currentResult = editorPane1.getText();
                    currentOperation = "+";
                    flag = true;
                    editorPane2.setText(temp);
                }

            }
        });

        minus_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(editorPane1.getText().length()<currentWordLen)
                {
                    Object[] options = { "OK", "CANCEL" };
                    int n = JOptionPane.showOptionDialog(panel, "The length of the input binary number is not long enough\nClick OK to continue", "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                }
                //editorPane2.getText().equals("")判断首次运算为减法
                if(editorPane2.getText().equals("") || !editorPane2.getText().equals("") && editorPane2.getText().substring(editorPane2.getText().length()-1).equals("=")){
                    currentResult=editorPane1.getText();
                    editorPane2.setText(editorPane1.getText() + "-");
                    currentOperation = "-";
                    clr1_flag = true;
                }
                else
                {

                    String temp = editorPane2.getText();
                    if(flag){
                        temp = temp.substring(0,temp.length()-1) + "-";
                    }
                    else{
                        if(!currentOperation.equals("NOT") && !currentOperation.equals("COMP") )
                        {
                            //无需按等号，直接输出结果
                            temp = temp + editorPane1.getText() + "-" ;
                            String temp1 = editorPane1.getText();
                            String temp2 = currentResult;
                            editorPane2.setText(editorPane2.getText() + temp1 + "=");
                            String result = add(temp2 , COMP(temp1));
                            if(!editorPane1.getText().equals(""))
                                editorPane1.setText(result);
                        }
                        else temp = temp +  "-" ;
                    }
                    currentResult = editorPane1.getText();
                    currentOperation = "-";
                    flag = true;
                    editorPane2.setText(temp);
                }

            }
        });

        ANDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentResult = editorPane1.getText();
                currentOperation = "AND";
                editorPane2.setText(currentResult + " AND ");
                editorPane1.setText("");
            }
        });

        ORButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentResult = editorPane1.getText();
                currentOperation = "OR";
                editorPane2.setText(currentResult + " OR ");
                editorPane1.setText("");
            }
        });

        NOTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentResult = editorPane1.getText();
                currentOperation = "NOT";
                editorPane2.setText("NOT("+currentResult + ")");
                editorPane1.setText("");
            }
        });

        XORButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentResult = editorPane1.getText();
                currentOperation = "XOR";
                editorPane2.setText(currentResult + " XOR ");
                editorPane1.setText("");

            }
        });

        COMPButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentResult = editorPane1.getText();
                currentOperation = "COMP";
                editorPane2.setText("COMP("+currentResult + ")");
                editorPane1.setText("");
            }
        });

        JRadioButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentWordLen = 8;
                JOptionPane.showMessageDialog(null, "当前运算字长已更新为8位");
                editorPane1.setText("");
                editorPane2.setText("");
                flag = false;
                clr1_flag = false;
                currentResult = "0";
            }
        });

        JRadioButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentWordLen = 16;
                JOptionPane.showMessageDialog(null, "当前运算字长已更新为16位");
                editorPane1.setText("");
                editorPane2.setText("");
                flag = false;
                clr1_flag = false;
                currentResult = "0";
            }
        });

        JRadioButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentWordLen = 32;
                JOptionPane.showMessageDialog(null, "当前运算字长已更新为32位");
                editorPane1.setText("");
                editorPane2.setText("");
                flag = false;
                clr1_flag = false;
                currentResult = "0";

            }
        });

        equal_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(editorPane1.getText().length()<currentWordLen && !currentOperation.equals("NOT") && !currentOperation.equals("COMP") )
                {
                    Object[] options = { "OK", "CANCEL" };
                    int n = JOptionPane.showOptionDialog(panel, "The length of the input binary number is not long enough\nClick OK to continue", "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                }
                if(currentOperation.equals("+"))
                {
                    //防止用户连续按两次等号
                    if(!editorPane2.getText().substring(editorPane2.getText().length()-1).equals("="))
                    {
                        String temp1 = editorPane1.getText();
                        String temp2 = currentResult;
                        editorPane2.setText(editorPane2.getText() + temp1 + "=");
                        String result = add(temp1 , temp2);
                        int len = result.length();
                        if(len < currentWordLen)
                        {
                            for(int j=currentWordLen; j>len;--j){
                                result = "0" + result;
                            }
                        }

                        editorPane1.setText(result);
                    }

                }
                if(currentOperation.equals("-"))
                {
                    if(!editorPane2.getText().substring(editorPane2.getText().length()-1).equals("="))
                    {
                        String temp1 = editorPane1.getText();
                        System.out.println("temp1: "+ temp1);
                        String temp2 = currentResult;
                        System.out.println("temp2: "+ temp2);
                        editorPane2.setText(editorPane2.getText() + temp1 + "=");
                        String result = add(temp2 , COMP(temp1));
                        if(temp1.equals(temp2) || result.equals("10000000") || result.equals("1000000000000000") || result.equals("10000000000000000000000000000000"))
                        {
                            result = "0";
                            editorPane1.setText("00000000");
                        }
                        else
                            editorPane1.setText(result.substring(1));
                    }

                }
                if(currentOperation.equals("AND"))
                {
                    if(!editorPane2.getText().substring(editorPane2.getText().length()-1).equals("="))
                    {
                        String temp1 = editorPane1.getText();
                        String temp2 = currentResult;
                        String result = AND(temp1 , temp2);
                        int len = result.length();
                        if(len < currentWordLen)
                        {
                            for(int j=currentWordLen; j>len;--j){
                                result = "0" + result;
                            }
                        }
                        editorPane1.setText(result);
                        editorPane2.setText(editorPane2.getText() + temp1 +"=");
                    }

                }

                if(currentOperation.equals("OR"))
                {
                    if(!editorPane2.getText().substring(editorPane2.getText().length()-1).equals("="))
                    {
                        String temp1 = editorPane1.getText();
                        String temp2 = currentResult;
                        String result = OR(temp1 , temp2);
                        int len = result.length();
                        if(len < currentWordLen)
                        {
                            for(int j=currentWordLen; j>len;--j){
                                result = "0" + result;
                            }
                        }
                        editorPane1.setText(result);
                        editorPane2.setText(editorPane2.getText() + temp1 +"=");
                    }

                }
                if(currentOperation.equals("NOT"))
                {
                    if(currentResult.charAt(0) == '1'){
                        String result = NOT(currentResult);
                        currentResult = result;
                    }
                    editorPane1.setText(currentResult);
                }
                if(currentOperation.equals("COMP"))
                {
                    if(currentResult.charAt(0) == '1'){
                        String result = COMP(currentResult);
                        currentResult = result;
                    }
                    editorPane1.setText(currentResult);
                }
                if(currentOperation.equals("XOR"))
                {
                    if(!editorPane2.getText().substring(editorPane2.getText().length()-1).equals("="))
                    {
                        String temp1 = editorPane1.getText();
                        String temp2 = currentResult;
                        String result = XOR(temp1 , temp2);
                        int len = result.length();
                        if(len < currentWordLen)
                        {
                            for(int j=currentWordLen; j>len;--j){
                                result = "0" + result;
                            }
                        }
                        editorPane1.setText(result);
                        editorPane2.setText(editorPane2.getText() + temp1 +"=");
                    }
                }

                int len = editorPane1.getText().length();
                //SF判断，符号标志位
                if(editorPane1.getText().charAt(0) == '1'){
                    sign_flag = 1;
                }

                //ZF判断，零标志位
                for(int i=0;i<len;i++){
                    if(editorPane1.getText().charAt(i) == '1'){
                        break;
                    }
                    if(i == len-1){
                        zero_flag = 1;
                    }
                }

    //CF判断，已在add()函数中实现
    //OF判断，符号数运算次高位和最高位向前进位或借位状态不一样时，OF=1
    if(overflow_flag == carry_flag){
        overflow_flag = 0;
    }
    else overflow_flag = 1;

    //PF判断，低8位中1的个数
    int sum = 0;
    int text1_len = editorPane1.getText().length();
    int t = currentWordLen;
    for(int i=text1_len-1;t>0;i--,t--){
        if(editorPane1.getText().charAt(i) == '1'){
            sum += 1;
        }
    }
    System.out.println(sum);
    if(sum % 2 ==0){
        parity_flag = 1;
    }

    FlagsShow();
    if(len > currentWordLen)
    {
        JOptionPane.showMessageDialog(panel, "Illegal error output: beyond word length range\nPlease re-enter", "Illegal error",JOptionPane.ERROR_MESSAGE);
    }
            }
        });
    }

    //标志位显示
    public void FlagsShow() {
        editorPane3.setText("        CF="+carry_flag+"     OF="+overflow_flag+"     ZF="+zero_flag+"\n        SF="+sign_flag+"     PF="+parity_flag+"     AF="+auxiliary_carry_flag);
    }

    //整数二进制相加
    public static String add(String b1, String b2) {
        int len1 = b1.length();
        int len2 = b2.length();
        String s1 = b1;
        String s2 = b2;
        StringBuilder sb1 = new StringBuilder();
        //先将位数较少的二进制高位补零
        if(len1 > len2) {
            for(int i = 0; i < (len1 - len2); i++) {
                sb1.append(0);
            }
            sb1.append(b2);
            s1 = b1;
            s2 = sb1.toString();
        } else if(len1 < len2) {
            for(int j = 0; j < (len2 - len1); j++) {
                sb1.append(0);
            }
            sb1.append(b1);
            s1 = sb1.toString();
            s2 = b2;
        }
        //进位
        int flag = 0;
        StringBuilder sb2 = new StringBuilder();
        for(int z = s1.length() - 1; z >= 0; z--) {
            //字符’0’的对应的ASCII十进制是48
            //分情况判断
            if((s1.charAt(z) - 48) == 0 && (s2.charAt(z) - 48) == 0) {
                sb2.append(flag);
                flag = 0;
                continue;
            }
            if(((s1.charAt(z) - 48) == 0 && (s2.charAt(z) - 48) == 1 && flag == 0) || ((s1.charAt(z) - 48) == 1 && (s2.charAt(z) - 48) == 0 && flag == 0)) {
                sb2.append(1);
                flag = 0;
                continue;
            }
            if(((s1.charAt(z) - 48) == 0 && (s2.charAt(z) - 48) == 1 && flag == 1) || ((s1.charAt(z) - 48) == 1 && (s2.charAt(z) - 48) == 0 && flag == 1)) {
                sb2.append(0);
                flag = 1;
                continue;
            }
            if((s1.charAt(z) - 48) == 1 && (s2.charAt(z) - 48) == 1 && flag == 0) {
                sb2.append(0);
                flag = 1;
                overflow_flag = 1;
                continue;
            }
            if((s1.charAt(z) - 48) == 1 && (s2.charAt(z) - 48) == 1 && flag == 1) {
                sb2.append(1);
                flag = 1;
            }

        }
        if(flag == 1) {
            sb2.append(flag);
            carry_flag = 1;
        }
        //倒置
        sb2.reverse();
        return sb2.toString();

    }

    //二进制数反码
    public static String NOT(String str){

        int len = str.length();
        char[] arr = str.toCharArray();
        if(len == currentWordLen && arr[0] == '1') {
            for (int i = 1; i < len; ++i) {
                if (arr[i] == '0') {
                    arr[i] = '1';
                } else
                    arr[i] = '0';
            }
        }
        else
            for (int i = 0; i < len; ++i) {
                if (arr[i] == '0') {
                    arr[i] = '1';
                } else
                    arr[i] = '0';
            }
        String notstr = new String(arr);
        StringBuilder temp = new StringBuilder(notstr);
        for(int i= currentWordLen-1; i>=str.length(); i--)
        {
            temp.insert(0, "1");
        }
        System.out.println("反码"+temp.toString());
        return temp.toString();
    }

    //二进制数补码
    public static String COMP(String str) {
        System.out.println("补码"+add(NOT(str),"1"));
        return add(NOT(str),"1");
    }

    //整数二进制与运算
    public static String AND(String b1, String b2){
        int len1 = b1.length();
        int len2 = b2.length();
        String s1 = b1;
        String s2 = b2;
        StringBuilder sb = new StringBuilder();
        //先将位数较少的二进制高位补零
        if(len1 > len2) {
            for(int i = 0; i < (len1 - len2); i++) {
                sb.append(0);
            }
            sb.append(b2);
            s1 = b1;
            s2 = sb.toString();
        } else if(len1 < len2) {
            for(int j = 0; j < (len2 - len1); j++) {
                sb.append(0);
            }
            sb.append(b1);
            s1 = sb.toString();
            s2 = b2;
        }
        StringBuilder result = new StringBuilder();
        char[] arr1 = s1.toCharArray();
        char[] arr2 = s2.toCharArray();
        for(int i=0; i<Math.max(len1,len2) ;++i){
            if(arr1[i] == '1' && arr2[i] == '1'){
                result.append('1');
            }
            else
                result.append('0');
        }
        return result.toString();
    }

    //整数二进制与运算
    public static String OR(String b1, String b2){
        int len1 = b1.length();
        int len2 = b2.length();
        String s1 = b1;
        String s2 = b2;
        StringBuilder sb = new StringBuilder();
        //先将位数较少的二进制高位补零
        if(len1 > len2) {
            for(int i = 0; i < (len1 - len2); i++) {
                sb.append(0);
            }
            sb.append(b2);
            s1 = b1;
            s2 = sb.toString();
        } else if(len1 < len2) {
            for(int j = 0; j < (len2 - len1); j++) {
                sb.append(0);
            }
            sb.append(b1);
            s1 = sb.toString();
            s2 = b2;
        }
        StringBuilder result = new StringBuilder();
        char[] arr1 = s1.toCharArray();
        char[] arr2 = s2.toCharArray();
        for(int i=0; i<Math.max(len1,len2) ;++i){
            if(arr1[i] == '0' && arr2[i] == '0'){
                result.append('0');
            }
            else
                result.append('1');
        }
        return result.toString();
    }

    //整数二进制异或运算
    public static String XOR(String b1, String b2){
        int len1 = b1.length();
        int len2 = b2.length();
        String s1 = b1;
        String s2 = b2;
        StringBuilder sb = new StringBuilder();
        //先将位数较少的二进制高位补零
        if(len1 > len2) {
            for(int i = 0; i < (len1 - len2); i++) {
                sb.append(0);
            }
            sb.append(b2);
            s1 = b1;
            s2 = sb.toString();
        } else if(len1 < len2) {
            for(int j = 0; j < (len2 - len1); j++) {
                sb.append(0);
            }
            sb.append(b1);
            s1 = sb.toString();
            s2 = b2;
        }
        StringBuilder result = new StringBuilder();
        char[] arr1 = s1.toCharArray();
        char[] arr2 = s2.toCharArray();
        for(int i=0; i<Math.max(len1,len2) ;++i){
            if((arr1[i] == '0' && arr2[i] == '1')||(arr1[i] == '1' && arr2[i] == '0')){
                result.append('1');
            }
            else
                result.append('0');
        }
        return result.toString();
    }

    public static void main(String[] args) {
            JFrame frame = new JFrame("二进制计算器");
            frame.setContentPane(new BinaryCalculator().panel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setSize(500, 400);
            frame.setVisible(true);
        }
}
