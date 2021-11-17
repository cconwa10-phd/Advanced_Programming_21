package com.example.helloworld;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class aminoAcidGui extends JFrame
{
    //private JLabel aminoAcid;
    private JButton startButton = new JButton("Start Quiz");
    private JButton cancelButton = new JButton("Cancel");
    private JButton submitButton = new JButton("Submit");
    JTextField time = new JTextField("Time Left: ");
    JTextField amiAcid = new JTextField();
    JTextField answer = new JTextField("");
    JTextField rAnswer = new JTextField("Right: ");
    JTextField wAnswer = new JTextField("Wrong: ");
    int right = 0;
    int wrong = 0;
    int index = 0;
    Random random = new Random();
    Timer timerD = new Timer();

    private void timerStart()
    {
        timerD.scheduleAtFixedRate(new TimerTask()
        {
            int seg = 30;
            @Override
            public void run()
            {
                time.setText("Time Left: " + seg);
                seg--;
                if(seg <= 0)
                {
                    timerD.cancel();
                    terminQuiz();
                }
            }
        }, 0, 1000);
    }


    private class StartQuiz implements ActionListener
    {
        public void actionPerformed(ActionEvent arg0)
        {
            timerStart();
            startQ();
            nextQuestion();
        }
    }
    private class SubmitQuiz implements ActionListener
    {
        public void actionPerformed(ActionEvent arg0)
        {
            checkAnswer();
            nextQuestion();
        }
    }
    private class EndQuiz implements ActionListener
    {
        public void actionPerformed(ActionEvent arg0)
        {
            terminQuiz();
        }
    }
    private void startQ()
    {
        right = 0;
        wrong = 0;
        rAnswer.setText("Right: ");
        wAnswer.setText("Wrong: ");
    }
    private void nextQuestion()
    {
        int aminoAnswer = random.nextInt(20);
        amiAcid.setText("input one-letter abbrevation for the following amino acid: " + FULL_NAMES[aminoAnswer]);
        answer.setText("");
        index = aminoAnswer;
        validate();
    }
    private void checkAnswer()
    {
        String answerAmino = answer.getText().toUpperCase();
        if(SHORT_NAMES[index].equals(answerAmino))
        {
            right += 1;
            rAnswer.setText("Right: " + right);
        }
        else
        {
            wrong += 1;
            wAnswer.setText("Wrong: " + wrong);
        }
        validate();
    }
    private void terminQuiz()
    {
        if(right != 0 || wrong !=0)
        {
            int totQ = right + wrong;
            float per = ((float) right/totQ)*100;
            amiAcid.setText("You have reached the end of the quiz with a percentage of: " + per + " %");
        }
        else
        {
            amiAcid.setText("You have reached the end of the quiz without answering questions");
        }
        answer.setText("END OF QUIZ");
        time.setText("END");
        validate();
    }

    public aminoAcidGui()
    {
        super("Amino Acid Quiz");
        setLocationRelativeTo(null);
        setSize(700,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel subPanel = new JPanel();
        JPanel subPanel2 = new JPanel();
        subPanel.add(startButton);
        subPanel.add(submitButton);
        subPanel.add(cancelButton);
        subPanel2.add(rAnswer);
        subPanel2.add(wAnswer);
        getContentPane().add(amiAcid, BorderLayout.CENTER);
        getContentPane().add(answer, BorderLayout.NORTH);
        getContentPane().add(subPanel, BorderLayout.SOUTH);
        getContentPane().add(subPanel2, BorderLayout.EAST);
        getContentPane().add(time, BorderLayout.WEST);
        startButton.addActionListener(new StartQuiz());
        submitButton.addActionListener(new SubmitQuiz());
        cancelButton.addActionListener(new EndQuiz());
        setVisible(true);
    }

    public static void main(String[] args)
    {
        new aminoAcidGui();

    }
    public static String[] SHORT_NAMES =
            { "A","R", "N", "D", "C", "Q", "E",
                    "G",  "H", "I", "L", "K", "M", "F",
                    "P", "S", "T", "W", "Y", "V"
            };

    public static String[] FULL_NAMES =
            {
                    "alanine","arginine", "asparagine",
                    "aspartic acid", "cysteine",
                    "glutamine",  "glutamic acid",
                    "glycine" ,"histidine","isoleucine",
                    "leucine",  "lysine", "methionine",
                    "phenylalanine", "proline",
                    "serine","threonine","tryptophan",
                    "tyrosine", "valine"
            };
}
