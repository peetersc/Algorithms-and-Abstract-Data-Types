class MatrixTest {

    /* Testing ------------------------------------------------------------ */

    public static void main(String args[]) 
    {
        Matrix A = new Matrix(300);
        Matrix B = new Matrix(300);
        Matrix C = new Matrix(300);


        //print A
        System.out.println("A has " + A.getNNZ() +  " non-zero entries:");
        System.out.println(A);

        //print B
        System.out.println("B has " + B.getNNZ() +  " non-zero entries:");
        System.out.println(B);

        //scalerMult
        C = A.scalarMult(1.5);
        System.out.println("(1.5)*A =");
        System.out.println(C);

                //scalerMult
        C = B.scalarMult(1.5);
        System.out.println("(1.5)*B =");
        System.out.println(C);

        C = A.add(B);
        System.out.println("A+B =");
        System.out.println(C.getNNZ());
        System.out.println(C);
        
        C = A.add(A);
        System.out.println("A+A =");
        System.out.println(C);

        C = B.sub(A);
        System.out.println("B-A =");
        System.out.println(C);

        C = A.sub(A);
        System.out.println("A-A =");
        System.out.println(C);

        C = B.sub(B);
        System.out.println("B-B =");
        System.out.println(C);

        C = A.transpose();
        System.out.println("transpose(A)");
        System.out.println(C);

        C = B.transpose();
        System.out.println("transpose(B)");
        System.out.println(C);

        C = A.mult(B);
        System.out.println("A*B =");
        System.out.println(C);

        C = B.mult(B);
        System.out.println("B*B =");
        System.out.println(C);

    }
}
