import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

class Room {
    public static final String A = "Люкс";
    public static final String B = "Стандарт";
    public static final String C = "Эконом";
    
    public static final int PRISE_FOR_A = 150;
    public static final int PRISE_FOR_B = 100;
    public static final int PRISE_FOR_C = 50;
    
    public int getPrice() {
        switch(roomClass) {
            case A:
                return PRISE_FOR_A;

            case B:
                return PRISE_FOR_B;
                
            case C:
                return PRISE_FOR_C;
        }
        return 0;
    }
    
    private String roomClass;
    private int places;
    private boolean free;
    
    public Room(String roomClass, int places, boolean free) {
        this.roomClass = roomClass;
        this.places = places;
        this.free = free;
    }
    
    public String getRoomClass() {
        return this.roomClass;
    }
    
    public int getPlaces() {
        return this.places;
    }
    
    public boolean isFree() {
        return this.free;
    }
    
    public void setFree(boolean isFree) {
        this.free = isFree;
    }
    
    public String toString() {
        return new String("КЛАСС: " + roomClass + "; МЕСТ: " + places);
    }
}

class Guest {
    private String passport;
    private Date arriveDate;
    private Date leaveDate;
    private int number;
    private boolean isNew;
    
    public Guest(int number, String passport, Date arriveDate, Date leaveDate, boolean isNew) {
        this.number = number;
        this.passport = passport;
        this.arriveDate = arriveDate;
        this.leaveDate = leaveDate;
        this.isNew = isNew;
    }
    
    public int getNumber() {
        return this.number;
    }
    
    public String getPassport() {
        return this.passport;
    }
    
    public Date getArriveDate() {
        return this.arriveDate;
    }
    
    public Date getLeaveDate() {
        return this.leaveDate;
    }
    
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        return new String(number + ". " + "ПАСПОРТ: " + passport + "; ОТ: " + dateFormat.format(arriveDate) + "; ДО: " + dateFormat.format(leaveDate));
    }
    
    public boolean isNew() {
        return this.isNew;
    }
    
    public void setIsNew(boolean flag) {
        this.isNew = flag;
    }
    
    public void setLeaveDate(Date newDate) {
        this.leaveDate = newDate;
    }
}

public class NewJFrame extends javax.swing.JFrame {
    
    private ArrayList<Room> rooms = new ArrayList<Room>(); // массив комнате
    private ArrayList<Guest> guests = new ArrayList<Guest>(); // гостей
    
    // модели (необходимы для работы с визуальными списками)
    private DefaultListModel modelForGuestsInQueue = new DefaultListModel();
    private DefaultListModel modelForCurrentGuests = new DefaultListModel();
    private DefaultListModel modelForFreeRooms = new DefaultListModel();
    
    // найти комнату по строковому представлению в массиве комнат; возвратить ее номер. Иначе позвратить -1
    private int findRoom(String room) {
       Iterator<Room> it1 = rooms.iterator();
            
        int i = 0;
        while (it1.hasNext()) {
            if (it1.next().toString().equals(room))
                return i;
            ++i;
        }
        return -1;
    }
    
    // найти гостя по сроковому представлению в массиве гостей; возвратить его номер. Иначе позвратить -1
    private int findGuest(String guest) {
        Iterator<Guest> it = guests.iterator();
        
        int i = 0;
        while (it.hasNext()) {
            if (it.next().toString().equals(guest))
                return i;
            ++i;
        }
        return -1;
    }
    
    // создать карту отношений гостей к занимаемым ими комнатам, определить компаратор
    private Map<Guest, Room> guestInRoom = new TreeMap<Guest, Room>(new Comparator<Guest>() {

        @Override
        public int compare(Guest o1, Guest o2) {
            String s1 = o1.getPassport() + o1.getNumber() + o1.getLeaveDate() + o1.getArriveDate();
            String s2 = o2.getPassport() + o2.getNumber() + o2.getLeaveDate() + o2.getArriveDate();
            return s1.compareTo(s2);
        }
    });

    // обновить список свободных комнат на экране
    private void updateFreeRoomsList() {
        modelForFreeRooms.clear(); // очистить соответствующий список
        
        Iterator<Room> it = rooms.iterator();
        while (it.hasNext()) { // цикл перебора комнат
            Room r = it.next();
            if (r.isFree()) // если комната пуста - добавить ее в список на экране
                modelForFreeRooms.addElement(r);
        }
    }
    
    // обновить список гостей в очереди на заселение на экране
    private void updateGuestsInQueueList() {
        modelForGuestsInQueue.clear(); // очистить соответствующий список
        
        Iterator<Guest> it = guests.iterator();
        while (it.hasNext()) {
            Guest g = it.next();
            if (g.isNew()) // если гость не заселен - добавить его в список на экране
                modelForGuestsInQueue.addElement(g);
        }
    }
    
    // обновить список текущих гостей на экране
    private void updateCurrentGuestsList() {
        modelForCurrentGuests.clear(); // очистить соответствующий список
        
            Iterator<Guest> it2 = guests.iterator();
            while (it2.hasNext()) {
                Guest g = it2.next();
                if (!g.isNew()) // если гость заселен - добавить в список на экране
                    modelForCurrentGuests.addElement(g);
            }
    }
    
    public NewJFrame() {
        initComponents();
        
        // добавить модели к спискам на экране
        guestInQueueList.setModel(modelForGuestsInQueue);
        freeRoomsList.setModel(modelForFreeRooms);
        currentGuestsList.setModel(modelForCurrentGuests);
        
        // создать номера
        rooms.add(new Room(Room.A, 4, true));
        rooms.add(new Room(Room.B, 2, true));
        rooms.add(new Room(Room.C, 1, true));
        rooms.add(new Room(Room.A, 3, true));
        rooms.add(new Room(Room.C, 2, true));
        
        // обновить список свободных комнат на экране
        updateFreeRoomsList();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addNewGuestPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        newGuestNumber = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        newGuestPassport = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        newGuestArriveDate = new javax.swing.JTextField();
        newGuestLeaveDate = new javax.swing.JTextField();
        addNewGuest = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        guestInQueueList = new javax.swing.JList();
        jLabel5 = new javax.swing.JLabel();
        takeRoom = new javax.swing.JButton();
        findString = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        currentGuestsList = new javax.swing.JList();
        extendGuestDuring = new javax.swing.JButton();
        daysCount = new javax.swing.JTextField();
        deleteGuest = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        freeRoomsList = new javax.swing.JList();
        jLabel8 = new javax.swing.JLabel();
        whatToSearch = new javax.swing.JTextField();
        findButton = new javax.swing.JButton();
        resultText = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel1.setText("Номер:");

        newGuestNumber.setText("1");

        jLabel2.setText("Паспорт:");

        newGuestPassport.setText("AN35");
        newGuestPassport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGuestPassportActionPerformed(evt);
            }
        });

        jLabel3.setText("Приезд:");

        jLabel4.setText("Отъезд:");

        newGuestArriveDate.setText("1.05.2014");
        newGuestArriveDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGuestArriveDateActionPerformed(evt);
            }
        });

        newGuestLeaveDate.setText("3.05.2014");

        addNewGuest.setText("Добавить в очередь");
        addNewGuest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewGuestActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout addNewGuestPanelLayout = new javax.swing.GroupLayout(addNewGuestPanel);
        addNewGuestPanel.setLayout(addNewGuestPanelLayout);
        addNewGuestPanelLayout.setHorizontalGroup(
            addNewGuestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addNewGuestPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addNewGuestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addNewGuestPanelLayout.createSequentialGroup()
                        .addGroup(addNewGuestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(addNewGuestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(newGuestPassport)
                            .addComponent(newGuestNumber)))
                    .addGroup(addNewGuestPanelLayout.createSequentialGroup()
                        .addGroup(addNewGuestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(13, 13, 13)
                        .addGroup(addNewGuestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addNewGuest, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(newGuestLeaveDate)
                            .addComponent(newGuestArriveDate))))
                .addContainerGap())
        );
        addNewGuestPanelLayout.setVerticalGroup(
            addNewGuestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addNewGuestPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addNewGuestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(newGuestNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(addNewGuestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(newGuestPassport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addNewGuestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(newGuestArriveDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addNewGuestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(newGuestLeaveDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addNewGuest)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        guestInQueueList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(guestInQueueList);

        jLabel5.setText("Список гостей на поселение:");

        takeRoom.setText("Поселить");
        takeRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                takeRoomActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(takeRoom)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(takeRoom))
        );

        jLabel6.setText("Список поселенных гостей:");

        currentGuestsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(currentGuestsList);

        extendGuestDuring.setText("Продлить на (дней)");
        extendGuestDuring.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                extendGuestDuringActionPerformed(evt);
            }
        });

        daysCount.setText("5");

        deleteGuest.setText("Выселить");
        deleteGuest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteGuestActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout findStringLayout = new javax.swing.GroupLayout(findString);
        findString.setLayout(findStringLayout);
        findStringLayout.setHorizontalGroup(
            findStringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(findStringLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(findStringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(findStringLayout.createSequentialGroup()
                        .addGroup(findStringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(findStringLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(findStringLayout.createSequentialGroup()
                                .addComponent(extendGuestDuring)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(daysCount, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 296, Short.MAX_VALUE)
                                .addComponent(deleteGuest)))
                        .addContainerGap())))
        );
        findStringLayout.setVerticalGroup(
            findStringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(findStringLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(findStringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(extendGuestDuring)
                    .addComponent(daysCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteGuest))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel7.setText("Свободные номера:");

        freeRoomsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(freeRoomsList);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3)
                .addContainerGap())
        );

        jLabel8.setText("Найти:");

        whatToSearch.setText("AN35");
        whatToSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                whatToSearchActionPerformed(evt);
            }
        });

        findButton.setText("Найти");
        findButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findButtonActionPerformed(evt);
            }
        });

        resultText.setText("Результат:");
        resultText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resultTextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(addNewGuestPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(findString, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(resultText)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(22, 22, 22)
                                .addComponent(whatToSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(findButton)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(addNewGuestPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(findString, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(whatToSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(findButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resultText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 5, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // добавить гостя в очередь
    private void addNewGuestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewGuestActionPerformed
        // считать данные о новом пользователе из полей на экране
        String passport = newGuestPassport.getText();
        int number = Integer.parseInt(newGuestNumber.getText());
        
        // считать данные о датах
        String arriveDateFromField = newGuestArriveDate.getText();
        String leaveDateFromField = newGuestLeaveDate.getText();
        
        // заметинить точки на слэшы для стандартизации формата
        arriveDateFromField = arriveDateFromField.replace('.', '/');
        leaveDateFromField = leaveDateFromField.replace('.', '/');
        
        // разбить даты на составляющие (день, месяц, год)
        String valuesFromArriveDate[] = arriveDateFromField.split("/");
        String valuesFromLeaveDate[] = leaveDateFromField.split("/");
        
        // записать значения в соответствующие переменные
        // дата приезда
        int arriveDay = Integer.parseInt(valuesFromArriveDate[0]);
        int arriveMonth = Integer.parseInt(valuesFromArriveDate[1]);
        int arriveYear = Integer.parseInt(valuesFromArriveDate[2]);
        
        // дата отъезда
        int leaveDay = Integer.parseInt(valuesFromLeaveDate[0]);
        int leaveMonth = Integer.parseInt(valuesFromLeaveDate[1]);
        int leaveYear = Integer.parseInt(valuesFromLeaveDate[2]);
        
        // создать даты, учитывая константы
        Date arriveDate = new Date(arriveYear-1900, arriveMonth-1, arriveDay);
        Date leaveDate = new Date(leaveYear-1900, leaveMonth-1, leaveDay);
        
        // добавить гостя в массив
        guests.add(new Guest(number, passport, arriveDate, leaveDate, true));

        // обновить списки на экране
        updateGuestsInQueueList();
    }//GEN-LAST:event_addNewGuestActionPerformed

    private void newGuestPassportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGuestPassportActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newGuestPassportActionPerformed

    private void newGuestArriveDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGuestArriveDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newGuestArriveDateActionPerformed

    // выселить гостя
    private void deleteGuestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteGuestActionPerformed
        int guestPos = currentGuestsList.getSelectedIndex();
        if (guestPos != -1) { // если гость был выбран
            int currentGuestPos = findGuest(modelForCurrentGuests.get(guestPos).toString());
            
            // запомнить гостя и его комнату
            Guest currentGuest = guests.get(currentGuestPos);
            Room currentRoom = guestInRoom.get(currentGuest);
            
            // рассчитать время пребывания и цену
            long daysCount = (currentGuest.getLeaveDate().getTime() - currentGuest.getArriveDate().getTime()) / (24 * 60 * 60 * 1000);
            long shouldPay = daysCount * currentRoom.getPrice();
            
            // пометить комнату, как пустую
            currentRoom.setFree(true);
            
            // удалить гостя из карты отношения гостей и занятых ими комнат
            guestInRoom.remove(currentGuest);
            guests.remove(currentGuestPos); // удалить гостя из массива гостей
            
            // обновить списки на экране
            updateFreeRoomsList();
            updateCurrentGuestsList();
            
            // показать квитанцию
            JOptionPane.showMessageDialog(this, "Гость: " + currentGuest + "\nПродолжительность пребывания (дни): " + daysCount + "\nНомер: "+ currentRoom+"\nПлата: " + shouldPay, "Квитаниция", JOptionPane.OK_OPTION);
        }
        else
            JOptionPane.showMessageDialog(this, "Выберите гостя из списка поселенных");
    }//GEN-LAST:event_deleteGuestActionPerformed

    private void takeRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_takeRoomActionPerformed
        // получить индексы выбранных элементов в списке на экране
        int roomPos = freeRoomsList.getSelectedIndex();
        int guestPos = guestInQueueList.getSelectedIndex();
        
        if (roomPos != -1 && guestPos != -1) { // если комната и гость были выбраны
            
            // найти их в массивах
            int currentGuestPos = findGuest(modelForGuestsInQueue.get(guestPos).toString());
            int currentRoomPos = findRoom(modelForFreeRooms.get(roomPos).toString());
            
            rooms.get(currentRoomPos).setFree(false); // занять комнату
            guests.get(currentGuestPos).setIsNew(false); // пометить гостя, как такого, что поселился
            guestInRoom.put(guests.get(currentGuestPos), rooms.get(currentRoomPos)); // добавить гостя в комнату
            
            // обновить списки на экране
            updateFreeRoomsList();
            updateCurrentGuestsList();
            updateGuestsInQueueList();
        }
        else
            JOptionPane.showMessageDialog(this, "Выберите номер и гостя, которого следует поселить");
    }//GEN-LAST:event_takeRoomActionPerformed

    // увеличить (уменьшить) продолжительность пребывания
    private void extendGuestDuringActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_extendGuestDuringActionPerformed
        int guestPos = currentGuestsList.getSelectedIndex();
        
        if (guestPos != -1) { // если гость был выбран
            int currentGuestPos = findGuest(modelForCurrentGuests.get(guestPos).toString()); // найти его в массиве
            
            // запомнить гостя и комнату
            Guest currentGuest = guests.get(currentGuestPos);
            Room currentRoom = guestInRoom.get(currentGuest);
            
            // узнать текущую дату отъезда в мс.
            long currentLeaveDateMs = currentGuest.getLeaveDate().getTime();
            int daysToAdd = Integer.parseInt(daysCount.getText()); // рассчитать кол-во мс, которые следует добавить к дате отъезда
            
            long newLeaveDateMs = currentLeaveDateMs + (daysToAdd * 24 * 60 * 60 * 1000); // расчитать новую дату отъезда
            
            guestInRoom.remove(currentGuest); // удалить гостя из карты отношений гостей к занимаемым ими комнатам
            
            currentGuest.setLeaveDate(new Date(newLeaveDateMs)); // установить новую дату отъезда
            
            guestInRoom.put(currentGuest, currentRoom); // добавить гостя в карты отношений гостей к занимаемым ими комнатам
            
            // обновить список текущих гостей на экране
            updateCurrentGuestsList();
        }
        else
            JOptionPane.showMessageDialog(this, "Выберите гостя из списка поселенных");
    }//GEN-LAST:event_extendGuestDuringActionPerformed

    private void findButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findButtonActionPerformed
        String key = whatToSearch.getText();
        
        Iterator<Guest> it = guests.iterator();
        while (it.hasNext()) {
            Guest g = it.next();
            String arriveDate = g.getArriveDate().toString();
            String leaveDate = g.getLeaveDate().toString();
            if (g.toString().contains(key)) {
                resultText.setText("Результат: " + g.toString());
                return;
            }
        }
        resultText.setText("Результат: ничего не найдено.");
    }//GEN-LAST:event_findButtonActionPerformed

    private void whatToSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_whatToSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_whatToSearchActionPerformed

    private void resultTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resultTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_resultTextActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addNewGuest;
    private javax.swing.JPanel addNewGuestPanel;
    private javax.swing.JList currentGuestsList;
    private javax.swing.JTextField daysCount;
    private javax.swing.JButton deleteGuest;
    private javax.swing.JButton extendGuestDuring;
    private javax.swing.JButton findButton;
    private javax.swing.JPanel findString;
    private javax.swing.JList freeRoomsList;
    private javax.swing.JList guestInQueueList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField newGuestArriveDate;
    private javax.swing.JTextField newGuestLeaveDate;
    private javax.swing.JTextField newGuestNumber;
    private javax.swing.JTextField newGuestPassport;
    private javax.swing.JTextField resultText;
    private javax.swing.JButton takeRoom;
    private javax.swing.JTextField whatToSearch;
    // End of variables declaration//GEN-END:variables
}
