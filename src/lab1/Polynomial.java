package lab1;

public class Polynomial {

    /**
      * @param size - max power of pride polynomial
     * @return byte array of polynomial`s coefficients
     * @throws Exception
     */
    static byte[] getPolynomial(int size) throws Exception {

        switch (size) {

            case 8:
                return new byte[]{8, 4, 3, 2};

            case 9:
                return new byte[]{9, 4};

            case 10:
                return new byte[]{10, 3};

            case 11:
                return new byte[]{11, 2};

            case 12:
                return new byte[]{12, 6, 4, 1};

            case 13:
                return new byte[]{13, 4, 3, 1};

            case 14:
                return new byte[]{14, 10, 6, 1};

            case 15:
                return new byte[]{15, 1};

            case 16:
                return new byte[]{16, 12, 3, 1};

            case 17:
               return new byte[]{17, 3};

            default:
                throw new Exception();
        }
    }
}
