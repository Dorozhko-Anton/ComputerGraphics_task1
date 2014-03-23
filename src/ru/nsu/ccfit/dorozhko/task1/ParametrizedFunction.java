package ru.nsu.ccfit.dorozhko.task1;

import ru.nsu.ccfit.dorozhko.MainFrame;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;

/**
 * Created by Anton on 10.03.14.
 */
public class ParametrizedFunction {
    private final ParametrizedFunctionPanel funcPanel;

    /**
     *  Ctor that manages adding menu items, actions, canvas to the mainFrame.
     *  May be I should do Init() method instead.
     */
    public ParametrizedFunction() {
        final MainFrame mainFrame = new MainFrame("Лабораторная работа №1");

        funcPanel = new ParametrizedFunctionPanel();
        mainFrame.addCanvas(funcPanel);


        AbstractAction moveUp = new AbstractAction("Вверх", MainFrame.createImageIcon("/images/up.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                funcPanel.setY0(funcPanel.getY0() - funcPanel.getMoveStep());
            }
        };
        AbstractAction moveDown = new AbstractAction("Вниз", MainFrame.createImageIcon("/images/down.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                funcPanel.setY0(funcPanel.getY0() + funcPanel.getMoveStep());
            }
        };
        AbstractAction moveRight = new AbstractAction("Вправо", MainFrame.createImageIcon("/images/right.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                funcPanel.setX0(funcPanel.getX0() + funcPanel.getMoveStep());
            }
        };
        AbstractAction moveLeft = new AbstractAction("Влево", MainFrame.createImageIcon("/images/left.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                funcPanel.setX0(funcPanel.getX0() - funcPanel.getMoveStep());
            }
        };

        mainFrame.addAction(moveUp);
        mainFrame.addAction(moveDown);
        mainFrame.addAction(moveRight);
        mainFrame.addAction(moveLeft);


        // scale + -
        AbstractAction decreaseScale = new AbstractAction("Уменьшить", MainFrame.createImageIcon("/images/dec.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (funcPanel.getUnitsX() - funcPanel.getScaleStep() > 10
                        && funcPanel.getUnitsY() - funcPanel.getScaleStep() > 10) {
                    funcPanel.setUnitsX(funcPanel.getUnitsX() - funcPanel.getScaleStep());
                    funcPanel.setUnitsY(funcPanel.getUnitsY() - funcPanel.getScaleStep());
                }
            }
        };
        AbstractAction increaseScale = new AbstractAction("Увеличить", MainFrame.createImageIcon("/images/inc.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                funcPanel.setUnitsX(funcPanel.getUnitsX() + funcPanel.getScaleStep());
                funcPanel.setUnitsY(funcPanel.getUnitsY() + funcPanel.getScaleStep());
            }
        };
        mainFrame.addAction(decreaseScale);
        mainFrame.addAction(increaseScale);


        // option panel

        final JCheckBox dragAndDropPermission = new JCheckBox("Drag'n'Drop");
        dragAndDropPermission.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                funcPanel.turnDragAndDrop(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        final SpinnerNumberModel stepSize = new SpinnerNumberModel(10, 5, 50, 1);
        stepSize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                funcPanel.setMoveStep((Integer) stepSize.getNumber());
            }
        });

        final JCheckBox wheelPermission = new JCheckBox("Включить прокрутку колесиком");
        wheelPermission.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                funcPanel.turnScaleByWheel(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        final SpinnerNumberModel scaleStepSize = new SpinnerNumberModel(10, 5, 50, 1);
        scaleStepSize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                funcPanel.setScaleStep((Integer) scaleStepSize.getNumber());
            }
        });

        final JPanel optionPanel = new JPanel(new GridLayout(2, 1));

        TitledBorder scaleBorder = BorderFactory.createTitledBorder("Масштаб");
        JPanel scalePanel = new JPanel(new GridLayout(2, 1));
        scalePanel.add(new JLabel("Шаг"));
        scalePanel.add(new JSpinner(scaleStepSize));
        scalePanel.add(wheelPermission);
        scalePanel.setBorder(scaleBorder);

        optionPanel.add(scalePanel);

        TitledBorder movementBorder = BorderFactory.createTitledBorder("Движение");
        JPanel movementPanel = new JPanel(new GridLayout(2, 1));
        movementPanel.add(new JLabel("Шаг"));
        movementPanel.add(new JSpinner(stepSize));
        movementPanel.add(dragAndDropPermission);
        movementPanel.setBorder(movementBorder);

        optionPanel.add(movementPanel);


        AbstractAction showOptionsPane = new AbstractAction("Настройки", MainFrame.createImageIcon("/images/options.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showConfirmDialog(mainFrame, optionPanel, "Настройки", JOptionPane.YES_NO_OPTION);
            }
        };
        mainFrame.addAction(showOptionsPane);
        JMenu navigation = new JMenu("Навигация");
        mainFrame.addMenu(navigation);

        navigation.add(moveUp);
        navigation.add(moveDown);
        navigation.add(moveRight);
        navigation.add(moveLeft);
        navigation.add(decreaseScale);
        navigation.add(increaseScale);
        navigation.add(showOptionsPane);


        AbstractAction contacts = new AbstractAction("Контакты", MainFrame.createImageIcon("/images/contact.png")) {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(mainFrame, "name: Ice \n mail: fff@gmail.com\n ");
            }
        };
        mainFrame.addAction(contacts);
        mainFrame.getAboutMenu().add(contacts);

        AbstractAction aboutAction = new AbstractAction("О программе", MainFrame.createImageIcon("/images/help_info2.png")) {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(mainFrame, "Лабораторная работа №1");
            }
        };
        mainFrame.addAction(aboutAction);
        mainFrame.getAboutMenu().add(aboutAction);
        AbstractAction exitAction = new AbstractAction("Выход", MainFrame.createImageIcon("/images/exit.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };
        mainFrame.addAction(exitAction);
        mainFrame.getFileMenu().add(exitAction);
        mainFrame.setVisible(true);
    }

    /**
     * Panel for plotting function.
     * Provides origin relative put Pixel, can pain Grid, can scale.
     */
    private class ParametrizedFunctionPanel extends JPanel {
        private int x0 = 400;
        private int y0 = 300;
        private int unitsX = 20;
        private int unitsY = 20;
        private int moveStep = 10;
        private double maxX = 100;
        private double minX = -100;
        private double maxY = 100;
        private double minY = -100;
        private int scaleStep = 10;
        private Point2D mousePt;
        private final MouseAdapter getPoint = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mousePt = e.getPoint();

            }
        };
        private final MouseMotionAdapter dragAndDropAxis = new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                x0 += e.getX() - mousePt.getX();
                y0 += e.getY() - mousePt.getY();
                mousePt = e.getPoint();
                repaint();
            }
        };
        private final MouseWheelListener scale = new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {

                if (unitsX - Math.signum(e.getUnitsToScroll()) * scaleStep > 10) {
                    unitsX -= Math.signum(e.getUnitsToScroll()) * scaleStep;
                }
                if (unitsY - Math.signum(e.getUnitsToScroll()) * scaleStep > 10) {
                    unitsY -= Math.signum(e.getUnitsToScroll()) * scaleStep;
                }
                repaint();

            }
        };

        {
            setSize(800, 600);
        }

        /**
         * Set pixel relative to origin
         * @param x - x coord
         * @param y - y coord
         * @param g - graphics
         */
        private void putPixel(int x, int y, Graphics g) {
            g.fillRect(x0 + x, y0 - y, 1, 1);
        }

        /**
         * Calculates screen borders.
         * Paint Grid & Function
         * @param g - graphics
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            paintGrid(g);
            maxX = ((getSize().getWidth() - x0) / unitsX);
            minX = -(double) x0 / unitsX;
            minY = (-(getSize().getHeight() - y0) / unitsY);
            maxY = (double) y0 / unitsY;

            g.setColor(Color.BLUE);

            PFunc pFunc = new PFunc();
            pFunc.paintPFunc(g);
        }

        /**
         * Paint grid starting from the origin (x0, y0) with steps unitX, unitY
         * @param g - graphics
         */
        private void paintGrid(Graphics g) {
            Dimension size = getSize();

            g.setColor(Color.LIGHT_GRAY);

            for (int x = x0; x < size.getWidth(); x += unitsX) {
                g.drawLine(x, 0, x, (int) size.getHeight());
            }
            for (int x = x0; x > 0; x -= unitsX) {
                g.drawLine(x, 0, x, (int) size.getHeight());
            }

            for (int y = y0; y < size.getHeight(); y += unitsY) {
                g.drawLine(0, y, (int) size.getWidth(), y);
            }
            for (int y = y0; y > 0; y -= unitsY) {
                g.drawLine(0, y, (int) size.getWidth(), y);
            }

            g.setColor(Color.BLACK);
            for (int y = 0; y < (int) size.getHeight(); y++) {
                g.fillRect(x0, y, 1, 1);
            }
            for (int x = 0; x < (int) size.getWidth(); x++) {
                g.fillRect(x, y0, 1, 1);
            }
        }

        /**
         * if b == true add MouseWheelListener that knows how to scale right.
         * @param b - boolean flag
         */
        public void turnScaleByWheel(boolean b) {
            if (b) {
                addMouseWheelListener(scale);
            } else {
                removeMouseWheelListener(scale);
            }
        }

        /**
         * if b == true add MouseListener that knows how to drag'n'drop right.
         * @param b - boolean flag
         */
        public void turnDragAndDrop(boolean b) {
            if (b) {
                addMouseListener(getPoint);
                addMouseMotionListener(dragAndDropAxis);
            } else {
                removeMouseListener(getPoint);
                removeMouseMotionListener(dragAndDropAxis);
            }
        }

        /**
         * Function by itself.
         *
         */
        private class PFunc {
            /**
             * function of X
             * @param t - parameter
             * @return x(t)
             */
            private double x(double t) {
                return (t * t / (t * t - 1));
            }

            /**
             * negative inverse of function of X
             * @param x - x(t)
             * @return  - t = x^-1(x(t))
             */
            private double minusInverseX(double x) {
                return -Math.sqrt(x / (x - 1));
            }
            /**
             * positive inverse of function of X
             * @param x -  x(t)
             * @return - t = x^-1(x(t))
             */
            private double plusInverseX(double x) {
                return Math.sqrt(x / (x - 1));
            }

            /**
             * derivative of X by t
             * @param t - parameter
             * @return dxdt(t)
             */
            private double dxdt(double t) {
                return -2 * t / ((t * t - 1) * (t * t - 1));
            }
            /**
             * function of Y
             * @param t - parameter
             * @return y(t)
             */
            private double y(double t) {
                return ((t * t + 1) / (t + 2));
            }
            /**
             * negative inverse of function of Y
             * @param y = y(t)
             * @return t = y^-1(y(t))
             */
            private double minusInverseY(double y) {
                return (y - Math.sqrt((y + 4) * (y + 4) - 20)) * 0.5;
            }
            /**
             * positive inverse of function of Y
             * @param y = y(t)
             * @return t = y^-1(y(t))
             */
            private double plusInverseY(double y) {
                return (y + Math.sqrt((y + 4) * (y + 4) - 20)) * 0.5;
            }
            /**
             * derivative of Y by t
             * @param t - parameter
             * @return dydt(t)
             */
            private double dydt(double t) {
                return 1 - 5 / ((t + 2) * (t + 2));
            }

            /**
             * divide function into continuous intervals and paint them separately
             * @param g - graphics
             */
            public void paintPFunc(Graphics g) {
                double Mx;
                double My;
                double M;
                double dt;
                double t1;
                double t2;
                double t;

                // t in [-inf; -2]
                if (maxX > 1 && minX < 4. / 3) {
                    if (minY < -4 - 2 * Math.sqrt(5)) {
                        Mx = 4. / 3;

                        t1 = plusInverseY(minY);
                        t2 = minusInverseY(minY);

                        if (Math.abs(t1 - (-2)) < Math.abs(t2 - (-2))) {
                            t = t1;
                        } else {
                            t = t2;
                        }

                        My = Math.abs(dydt(t));
                        M = Math.max(Mx, My);
                        dt = 1. / (M * Math.max(unitsX, unitsY));

                        t = -2 - Math.sqrt(5);
                        while (y(t) > minY) {
                            putPixel((int) Math.floor(x(t) * unitsX), (int) Math.floor(y(t) * unitsY), g);
                            t -= dt;
                        }
                        t = -2 - Math.sqrt(5);
                        while (y(t) > minY) {
                            putPixel((int) Math.floor(x(t) * unitsX), (int) Math.floor(y(t) * unitsY), g);
                            t += dt;
                        }
                    }
                }
                // t in [-2, -1]
                if (maxX > 4. / 3) {
                    if (maxY > 2) {
                        t1 = plusInverseX(maxX);
                        t2 = minusInverseX(maxX);

                        if (Math.abs(t1 - (-1)) < Math.abs(t2 - (-1))) {
                            t = t1;
                        } else {
                            t = t2;
                        }

                        Mx = Math.abs(dxdt(t));

                        t1 = plusInverseY(maxY);
                        t2 = minusInverseY(maxY);

                        if (Math.abs(t1 - (-2)) < Math.abs(t2 - (-2))) {
                            t = t1;
                        } else {
                            t = t2;
                        }

                        My = Math.abs(dydt(t));
                        M = Math.max(Mx, My);

                        dt = 1. / (M * Math.max(unitsX, unitsY));

                        double tend = minusInverseX(maxX);
                        t = minusInverseY(maxY);
                        while (t < tend) {
                            putPixel((int) Math.floor(x(t) * unitsX), (int) Math.floor(y(t) * unitsY), g);
                            t += dt;
                        }
                    }
                }

                // t in [-1, 1]
                if (minX <= 0) {
                    if (maxY > -4 + 2 * Math.sqrt(5) && minY < 2) {
                        t1 = plusInverseX(minX);
                        t2 = minusInverseX(minX);
                        if (Math.abs(t1 - (-1)) < Math.abs(t2 - (-1))) {
                            t = t1;
                        } else {
                            t = t2;
                        }
                        Mx = Math.abs(dxdt(t));
                        My = 4;
                        M = Math.max(Mx, My);
                        dt = 1. / (M * Math.max(unitsX, unitsY));
                        double tend = plusInverseX(minX);
                        t = 0;
                        while (t < tend) {
                            putPixel((int) Math.floor(x(t) * unitsX), (int) Math.floor(y(t) * unitsY), g);
                            t += dt;
                        }
                        tend = -tend;
                        t = 0;
                        while (t > tend) {
                            putPixel((int) Math.floor(x(t) * unitsX), (int) Math.floor(y(t) * unitsY), g);
                            t -= dt;
                        }
                    }
                }
                //t in [1, inf)
                if (maxX > 1) {
                    if (maxY > 2. / 3) {
                        t1 = plusInverseX(maxX);
                        t2 = minusInverseX(maxX);
                        if (Math.abs(t1 - (1)) < Math.abs(t2 - (1))) {
                            t = t1;
                        } else {
                            t = t2;
                        }
                        Mx = Math.abs(dxdt(t));

                        My = 1;
                        M = Math.max(Mx, My);
                        dt = 1. / (M * Math.max(unitsX, unitsY));

                        double tbegin = plusInverseY(maxY);
                        if (tbegin < 1) {
                            tbegin = minusInverseY(maxY);
                        }

                        t = tbegin;
                        double tend = plusInverseX(maxX);

                        while (t > tend) {
                            putPixel((int) Math.floor(x(t) * unitsX), (int) Math.floor(y(t) * unitsY), g);
                            t -= dt;
                        }

                    }
                }

            }
        }



        public int getScaleStep() {
            return scaleStep;
        }

        public void setScaleStep(int scaleStep) {
            this.scaleStep = scaleStep;
        }

        public int getMoveStep() {
            return moveStep;
        }

        public void setMoveStep(int moveStep) {
            this.moveStep = moveStep;
        }
        public int getX0() {
            return x0;
        }

        public int getY0() {
            return y0;
        }

        public int getUnitsX() {
            return unitsX;
        }

        public void setUnitsX(int unitsX) {
            this.unitsX = unitsX;
            repaint();
        }

        public int getUnitsY() {
            return unitsY;
        }

        public void setUnitsY(int unitsY) {
            this.unitsY = unitsY;
            repaint();
        }

         public void setX0(int x0) {
            this.x0 = x0;
            repaint();
        }

        public void setY0(int y0) {
            this.y0 = y0;
            repaint();
        }
    }

    public static void main(final String... args) {
        new ParametrizedFunction();
    }
}
