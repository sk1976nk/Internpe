import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Graphics extends JPanel implements ActionListener {

    static final int WIDTH = 700;
    static final int HEIGHT = 700;

    static final String MARK_X = "X";
    static final String MARK_0 = "0";

    final JButton[] title = new JButton[9];
    boolean isFirstPlayerActive;

    public Graphics() {
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.setLayout(new GridLayout(3,3));

        for(int i = 0; i<9; i++){
            title[i] = new JButton();
            title[i].setFont(new Font("Arial", Font.BOLD,125));
            title[i].setFocusable(false);
            title[i].addActionListener(this);
            this.add(title[i]);
        }

        isFirstPlayerActive = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i =0; i < 9;i++ ){
            if(e.getSource() == title[i]){
                if (title[i].getText().isEmpty()){
                    if(isFirstPlayerActive) {
                        title[i].setForeground(Color.BLACK);
                        title[i].setText(MARK_X);
                        isFirstPlayerActive = false;
                    }else{
                        title[i].setForeground(Color.BLUE);
                        title[i].setText(MARK_0);
                        isFirstPlayerActive = true;
                    }

                    checkState();
                }
            }
        }

    }

    protected void checkState(){
        if (checkMark(MARK_X)){
            return;
        }

        if (checkMark(MARK_0)){
            return;
        }

        if (checkDraw()) {
            return;
        }

    }

    protected  boolean checkDraw(){
        int i = 0;
        while(!title[i].getText().isEmpty()){
            if (i == title.length -1){
                Arrays.stream(title).forEach(t -> t.setEnabled(false));
                break;
            }
            i++;
        }

        return i == title.length -1;
    }


    protected boolean checkMark(String mark){
        boolean isDone =false;

        // horizontal
        isDone = checkDirection(0,1,2,mark);
        if (!isDone) isDone = checkDirection(3, 4, 5, mark);
        if (!isDone) isDone = checkDirection(6, 7, 8, mark);

        // vertical
        if (!isDone) isDone = checkDirection(0, 3, 6, mark);
        if (!isDone) isDone = checkDirection(1, 4, 7, mark);
        if (!isDone) isDone = checkDirection(2, 5, 8, mark);

        //diagonal
        if (!isDone) isDone = checkDirection(0, 4, 8, mark);
        if (!isDone) isDone = checkDirection(2, 4, 6, mark);


        return  isDone;

    }

    protected boolean checkDirection(int posA ,int posB ,int posC ,String mark){
        if (title[posA].getText().equals(mark) && title[posB].getText().equals(mark) && title[posC].getText().equals(mark)){
            setWinner(posA, posB, posC);
            return true;
        }

        return false;
    }

    protected void setWinner(int posA, int posB, int posC){
        title[posA].setBackground(Color.GREEN);
        title[posB].setBackground(Color.GREEN);
        title[posC].setBackground(Color.GREEN);

        Arrays.stream(title).forEach(t -> t.setEnabled(false));
    }




}
