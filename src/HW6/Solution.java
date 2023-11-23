package HW6;

class Solution {
    public String convert(String s, int numRows) {
        int length = s.length();
        int column = (length+1)/2;
        char[][] zigZag = new char[numRows][column];
        int numAt = 0;
        String answer = null;
        if (numRows != 1) {

            for (int i = 0; i < column; i++) {
                if (i%(numRows-1) == 0) {
                    for (int j = 0; j< numRows; j++) {
                        zigZag[j][i] = s.charAt(numAt);
                        numAt++;
                    }
                } else {
                    zigZag[i%numRows-2][i] = s.charAt(numAt);
                    numAt++;
                }
            }
        } else if (numRows == 1) {
            return s;
        }

        for (int j = 0; j < numRows; j++) {
            for (int i = 0; i < column; i++) {

                if (Character.isWhitespace(zigZag[j][i])) {
                    continue;
                } else {
                    answer = answer + zigZag[i][j];
                }
            }
        }
        return answer;
    }
}