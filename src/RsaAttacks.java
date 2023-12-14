import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Scanner;

public class RsaAttacks {
    public static BigInteger e = new BigInteger("10001", 16);
    public static BigInteger e1 = new BigInteger("5", 10);
    public static BigInteger ten = new BigInteger("10", 10);

    public static BigInteger power(BigInteger N, BigInteger pow){
        BigInteger staticN = N;
        String BitPow = pow.toString(2);
        if(pow.equals(BigInteger.ZERO))
        {
            return BigInteger.ONE;
        }
        for(int i=1; i<BitPow.length(); i++){
            N = N.pow(2);
            if(BitPow.charAt(i) == '1'){
                N=N.multiply(staticN);
            }
        }
        return N;
    }

    public static ArrayList<BigInteger> China(ArrayList<BigInteger> Ci, ArrayList<BigInteger> Ni) {

        BigInteger N = new BigInteger("1",10);
        BigInteger C = new BigInteger("1", 16);
        ArrayList<BigInteger> Mi = new ArrayList<>();
        ArrayList<BigInteger> ni = new ArrayList<>();
        for(int i =0; i< Ni.size();i++) {
            N = N.multiply(Ni.get(i));
            C = C.multiply(Ci.get(i));
        }
        ArrayList<BigInteger> sol = new ArrayList<>();
        sol.add(N);
        for (BigInteger bigInteger : Ni) {
            BigInteger Mt = N.divide(bigInteger);
            Mi.add(Mt);
        }
        for(int i =0; i< Mi.size();i++) {
            BigInteger Nt = Mi.get(i).modInverse(Ni.get(i));
            ni.add(Nt);
        }
        BigInteger X = new BigInteger("0",10);
        for(int i = 0; i < Ci.size(); i++) {
            X = X.add((ni.get(i).multiply(Ci.get(i))).multiply(Mi.get(i)));
        }
        X = X.mod(N);
        sol.add(X);
        return sol;
    }

    public static String NumbRoot(BigInteger A)
    {
        String a = A.toString(10);
        String answer;
        ArrayList<String> delimnum = new ArrayList<>();
        for(int i = 0; i < a.length(); i=i+e1.intValue())
        {
            if(a.length()%e1.intValue() != 0 && i == 0)
            {
                int q = a.length()%e1.intValue();
                String temp = a.substring(i, q);
                delimnum.add(temp);
                i=i+q;
            }
            String temp = a.substring(i,i+e1.intValue());
            delimnum.add(temp);
        }
        int q1 = Integer.parseInt(delimnum.get(0));
        double deg = 1.0/5.0;
        int tempa = (int) Math.pow(q1, deg);
        BigInteger temp = new BigInteger(Integer.toString(tempa), 10);
        answer = String.valueOf(temp);
        int temp2 = q1 - (int) Math.pow(tempa, e1.intValue());
        String lim = String.valueOf(temp2);
        for (int i = 1; i< delimnum.size(); i++){
            lim = lim + delimnum.get(i);
            BigInteger limi = new BigInteger(lim,10);
            ArrayList<BigInteger> form = new ArrayList<>();
            for(int j = 0; j < e1.intValue(); j++)
            {
                BigInteger application = C(e1, new BigInteger(String.valueOf(j+1), 10));
                application = application.multiply(power(ten, e1.subtract(BigInteger.valueOf(j+1))));
                BigInteger pow = power(new BigInteger(String.valueOf(temp)), e1.subtract(BigInteger.valueOf(j+1)));
                application = application.multiply(pow);
                form.add(application);
            }
            boolean signal = false;
            BigInteger sum = new BigInteger("0", 10);
            BigInteger x = new BigInteger("0",10);
            while (!signal)
            {
                BigInteger x1, sum1;
                sum1 = BigInteger.ZERO;
                x = x.add(BigInteger.ONE);
                x1 = x;
                for(int j = 0; j < form.size();j++){
                    sum1 = sum1.add(x1.multiply(form.get(j)));
                    if(sum1.compareTo(limi) > 0){
                        x = x.subtract(BigInteger.ONE);
                        signal = true;
                        break;
                    }
                    x1 = x1.multiply(x);
                }
                if(!signal) sum = sum1;
            }
            answer = answer + x.toString(10);
            temp = new BigInteger(answer,10);
            limi = limi.subtract(sum);
            lim = limi.toString(10);
        }
        System.out.println(answer);
        return answer;
    }

    public static BigInteger C(BigInteger n, BigInteger k)
    {
        BigInteger C;
        C = factorial(n).divide(factorial(k));
        C = C.divide(factorial(n.subtract(k)));
        return C;
    }

    public static BigInteger factorial(BigInteger n)
    {
        BigInteger res = BigInteger.valueOf(1);
        for (int i = 2; i <= n.intValue(); i++){
            res = res.multiply(BigInteger.valueOf(i));
        }
        return res;
    }

    public static final BigInteger _3=BigInteger.valueOf(3);

    /*public static BigInteger cbrt(BigInteger n)
    {
        BigInteger root=BigInteger.ZERO.setBit(n.bitLength()/3),t1,t2,t3;
        t1=t2=t3=BigInteger.ZERO;
        do {
            t3=t2;t2=t1;t1=root;
            root=t1.add(t1).add(n.divide(t1.multiply(t1))).divide(_3);
        } while(!root.equals(t1.add(t2).add(t3).divide(_3)));
        System.out.println(root.toString(16));
        return root;
    }*/

    public static void main(String[] args) throws Exception {
        ArrayList<String> parse = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("C:\\01.txt"));
        String line;
        Scanner scanner;
        while ((line = reader.readLine()) != null) {
            scanner = new Scanner(line);
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                String num = scanner.next();
                parse.add(num);
            }
        }
        System.out.println(parse);
        ArrayList<BigInteger> Ci = new ArrayList<>();
        ArrayList<BigInteger> Ni = new ArrayList<>();
        for(int i =0; i< parse.size(); i++)
        {
            String temp = parse.get(i);
            temp = temp.substring(7,parse.get(i).length());
            if(i %2!=0) Ni.add(new BigInteger(temp,16));
            else Ci.add(new BigInteger(temp,16));
        }
        long t1 = System.currentTimeMillis();
        ArrayList<BigInteger> NX = China(Ci,Ni);
        BigInteger N = NX.get(0);
        BigInteger C = NX.get(1);
        System.out.println("N = " + N.toString(16));
        System.out.println("C=M^" + e1 + " = " + C.mod(N).toString(16));
        String nub = NumbRoot(C);
        BigInteger Mexp = new BigInteger(nub, 10);
        System.out.println("M = " + Mexp.toString(16));
        System.out.println("Check root");
        System.out.println("M^e = " + Mexp.modPow(e1,N).toString(16));
        long t2 = System.currentTimeMillis();
        System.out.println("Times attack : " + (t2-t1) + "mls");
        System.out.println("_____________________________________________________________________");


        parse = new ArrayList<>();
        reader = new BufferedReader(new FileReader("C:\\01_Mit.txt"));
        while ((line = reader.readLine()) != null) {
            scanner = new Scanner(line);
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                String num = scanner.next();
                num = num.substring(6);
                parse.add(num);
            }
        }
        C = new BigInteger(parse.get(0),16);
        N = new BigInteger(parse.get(1), 16);
        System.out.println("C = " + C.toString(16));
        System.out.println();
        System.out.println("N = " + N.toString(16));
        long l;
        l = (int) Math.pow(2,22);
        t1 = System.currentTimeMillis();
        ArrayList<BigInteger> X = new ArrayList<>();
        ArrayList<BigInteger> T = new ArrayList<>();
        int flag = -1, flagX = 0;
        //Calculate T^e and S^-e
        for(long i =1; i < l; i++)
        {
            BigInteger temp = new BigInteger(String.valueOf(i));
            X.add(temp.modPow(e,N));
        }
        //Calculate Cs
        BigInteger RCs = new BigInteger("1");
        for (int i = 0; i < X.size(); i++) {
            T.add(X.get(i).modInverse(N));
            BigInteger Cs = C.multiply(T.get(i)).mod(N);
            for (int j = 0; j < X.size(); j++) {
                if (Cs.equals(X.get(j))) {
                    flag = j;
                    break;
                }
            }
            if (flag != -1)
            {
                RCs = X.get(i);
                flagX = i+1;
                break;
            }
        }
        if(flag == -1) System.out.println("Didn`t find any");
        else {
            System.out.println("Answer" + flag);

            //Verify
            System.out.println("Expected -- M1 = " + flagX);
            System.out.println("Expected -- M2 = " + (flag + 1));
            System.out.println("(M1*M2)^e = ");
            BigInteger Cnew = RCs.multiply(X.get(flag));
            System.out.println(Cnew.mod(N).toString(16));
            System.out.println("Real C = ");
            System.out.println(C.toString(16));
            t2 = System.currentTimeMillis();
            System.out.println("Times attack : " + (t2-t1) + "mls");
        }
    }
}
