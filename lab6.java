package com.example.helloworld;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Semaphore;
import java.util.List;

public class lab6 extends JFrame
{
    public static final List<Double> primeList = Collections.synchronizedList(new ArrayList<>());
    private static long startTime;
    private static boolean cancel = false;
    //public static final List<Double> sPrimeList = new ArrayList<>();
    private JButton startButton = new JButton("Submit");
    private JButton cancelButton = new JButton("Cancel");
    private static final JTextArea primeNumIn = new JTextArea(10,20);
    //JTextField time = new JTextField("");
    JTextField threads = new JTextField("Please enter number of threads HERE");
    JTextField labelInput = new JTextField("Please enter number for prime calculations HERE");

    public static void checkIfPrime(double value)
    {
        boolean prime = true;
        if(value == 1 || value == 2 || value == 3)
        {
            primeList.add(value);
        }
        else
        {
            for(double i = 4; i <= value/2; ++i)
            {
                if(value % i == 0)
                {
                    prime = false;
                    break;
                }

            }
            if(prime)
            {
                primeList.add(value);
            }
        }
    }
    public static void loopCheckIfPrime(double start, double end)
    {
        for(double j = start; j < end; j++)
        {
            checkIfPrime(j);
        }
    }

    //multi-thread, have a volatile cancel flag
    private void setThreadsToCalcPrime(int numThreads, double value) throws InterruptedException
    {
        Semaphore semaphore = new Semaphore(numThreads);
        double range = Math.round(value/numThreads);
        for(int i = 1; i < numThreads; i++)
        {
            semaphore.acquire();
            MultiThreadPrime worker = new MultiThreadPrime(numThreads, value, range, i, semaphore);
            new Thread(worker).start();
        }
        MultiThreadManager multiThreadManager = new MultiThreadManager(semaphore, numThreads);
        new Thread(multiThreadManager).start();
    }

    public class MultiThreadPrime implements Runnable
    {
        private final int numThreads;
        private final double num;
        private double range;
        private double index;
        private final Semaphore semaphore;
        public MultiThreadPrime(int numThreads, double num, double range, double index, Semaphore semaphore)
        {
            this.numThreads = numThreads;
            this.num = num;
            this.range = range;
            this.index = index;
            this.semaphore = semaphore;
        }
        @Override
        public void run()
        {
            if(cancel == false)
            {
                if(numThreads == 1d)
                {
                   loopCheckIfPrime(1d, num);
                }
                else
                {
                    if(index == 1d)
                    {
                        loopCheckIfPrime(index, range-1);
                    }
                    else if(index == numThreads)
                    {
                        double endRange = range + (num - (range*numThreads));
                        loopCheckIfPrime((index-1)*range, endRange);
                    }
                    else
                    {
                        loopCheckIfPrime((index-1)*range, (range*index) - 1);
                    }
                }
            }
            else
            {
                primeNumIn.append("Process Terminated\n");
                semaphore.release();
                //break;
            }
            semaphore.release();
        }
    }
    private class MultiThreadManager implements Runnable
    {
        private final Semaphore mSemaphore;
        private final int numThreads;
        public MultiThreadManager(Semaphore mSemaphore, int numThreads)
        {
            this.mSemaphore = mSemaphore;
            this.numThreads = numThreads;
        }
        @Override
        public void run()
        {
            int threadTracker = 0;
            while(threadTracker < numThreads)
            {
                try
                {
                    mSemaphore.acquire();
                    threadTracker = threadTracker + 1;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            long stopTime = System.currentTimeMillis();
            primeNumIn.append("total time: " + (stopTime - startTime)/1000F + "s \n");
            mSemaphore.release();
            primeNumIn.append("total number of prime values " + primeList.size());
        }
    }
    private class StartButton implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            boolean loop = false;
            while(loop == false)
            {
                if(threads.getText() != null && labelInput.getText() != null )
                try
                {
                    int numThreads = Integer.parseInt(threads.getText());
                    double value = Double.parseDouble(labelInput.getText());
                    loop = true;
                    primeNumIn.setText("");
                    primeList.clear();
                    cancel = false;
                    startTime = System.currentTimeMillis();
                    setThreadsToCalcPrime(numThreads, value);
                }catch(NumberFormatException numberFormatException)
                {
                    JOptionPane.showMessageDialog(primeNumIn, "Please input a valid number");
                    loop = false;
                }catch (HeadlessException | InterruptedException headlessException)
                {
                    headlessException.printStackTrace();
                }
            }
            validate();
        }
    }
    private class CancelButton implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            cancel = true;
            validate();
        }
    }
    public lab6()
    {
        super("Prime Number Search");
        JScrollPane primeNum = new JScrollPane(primeNumIn);
        setLocationRelativeTo(null);
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel subPanel = new JPanel();
        primeNum.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        primeNum.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        subPanel.add(startButton);
        subPanel.add(cancelButton);
        JPanel subpanel2 = new JPanel();
        subpanel2.add(threads);
        subpanel2.add(labelInput);
        getContentPane().add(subPanel, BorderLayout.SOUTH);
        //getContentPane().add(time, BorderLayout.WEST);
        getContentPane().add(primeNum, BorderLayout.CENTER);
        getContentPane().add(subpanel2, BorderLayout.NORTH);
        startButton.addActionListener(new StartButton());
        cancelButton.addActionListener(new CancelButton());
        setVisible(true);

    }
    public static void main(String[] args)
    {
        new lab6();
    }

}
