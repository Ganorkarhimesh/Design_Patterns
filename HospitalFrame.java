package com.smarthospital.view;

import com.smarthospital.controller.HospitalController;
import com.smarthospital.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class HospitalFrame extends JFrame {
    private final HospitalController c;
    private final DefaultTableModel patientTable = model("ID","Name","Age","Gender","Phone","Condition");
    private final JTable patientsGrid = new JTable(patientTable);
    private final DefaultTableModel appointmentTable = model("ID","Patient","Date","Time","Doctor","Reason","Status");
    private final DefaultTableModel billTable = model("ID","Patient","Date","Description","Amount","Payment");
    private final DefaultTableModel treatmentTable = model("ID","Patient","Date","Diagnosis","Treatment","Notes");
    private final JTextField name=new JTextField(), age=new JTextField(), phone=new JTextField(), disease=new JTextField();
    private final JComboBox<String> gender=new JComboBox<>(new String[]{"Female","Male","Other"});
    private int selectedPatientId=0;
    private final java.util.List<JComboBox<String>> patientCombos = new java.util.ArrayList<>();

    public HospitalFrame(HospitalController controller) {
        super("Smart Hospital Management System");
        c=controller;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);setSize(1100,700);setLocationRelativeTo(null);
        setLayout(new BorderLayout(8,8));
        JLabel header=new JLabel("SMART HOSPITAL MANAGEMENT SYSTEM",SwingConstants.CENTER);
        header.setFont(new Font("SansSerif",Font.BOLD,22));header.setOpaque(true);header.setBackground(new Color(28,78,125));header.setForeground(Color.WHITE);header.setBorder(BorderFactory.createEmptyBorder(16,8,16,8));
        add(header,BorderLayout.NORTH);
        JTabbedPane tabs=new JTabbedPane();
        tabs.addTab("Patients",patientPanel());tabs.addTab("Appointments",appointmentPanel());tabs.addTab("Billing",billPanel());tabs.addTab("Treatment Records",treatmentPanel());
        add(tabs,BorderLayout.CENTER);
        refreshAll();
    }
    private JPanel patientPanel(){
        JPanel outer=new JPanel(new BorderLayout(8,8));outer.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        JPanel form=new JPanel(new GridLayout(2,5,8,6));
        form.add(new JLabel("Full name"));form.add(new JLabel("Age"));form.add(new JLabel("Gender"));form.add(new JLabel("Phone"));form.add(new JLabel("Condition / disease"));
        form.add(name);form.add(age);form.add(gender);form.add(phone);form.add(disease);
        JButton save=new JButton("Register / Update"),clear=new JButton("Clear"),delete=new JButton("Delete selected"),refresh=new JButton("Refresh");
        JPanel buttons=new JPanel(new FlowLayout(FlowLayout.LEFT));buttons.add(save);buttons.add(clear);buttons.add(delete);buttons.add(refresh);
        JPanel north=new JPanel(new BorderLayout());north.add(form,BorderLayout.CENTER);north.add(buttons,BorderLayout.SOUTH);outer.add(north,BorderLayout.NORTH);
        patientsGrid.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);outer.add(new JScrollPane(patientsGrid),BorderLayout.CENTER);
        patientsGrid.getSelectionModel().addListSelectionListener(e->{if(!e.getValueIsAdjusting()&&patientsGrid.getSelectedRow()>=0){
            int r=patientsGrid.convertRowIndexToModel(patientsGrid.getSelectedRow());selectedPatientId=(int)patientTable.getValueAt(r,0);
            name.setText(String.valueOf(patientTable.getValueAt(r,1)));age.setText(String.valueOf(patientTable.getValueAt(r,2)));
            gender.setSelectedItem(patientTable.getValueAt(r,3));phone.setText(String.valueOf(patientTable.getValueAt(r,4)));disease.setText(String.valueOf(patientTable.getValueAt(r,5)));
        }});
        save.addActionListener(e->run(()->{c.savePatient(selectedPatientId,name.getText(),age.getText(),String.valueOf(gender.getSelectedItem()),phone.getText(),disease.getText());clearPatient();refreshAll();message("Patient saved.");}));
        clear.addActionListener(e->clearPatient());
        delete.addActionListener(e->run(()->{if(selectedPatientId==0)throw new IllegalArgumentException("Select a patient first.");int ok=JOptionPane.showConfirmDialog(this,"Delete patient and linked records?","Confirm",JOptionPane.YES_NO_OPTION);if(ok==JOptionPane.YES_OPTION){c.deletePatient(selectedPatientId);clearPatient();refreshAll();message("Patient deleted.");}}));
        refresh.addActionListener(e->refreshAll());
        return outer;
    }
    private JPanel appointmentPanel(){
        JTextField date=new JTextField(LocalDate.now().toString()),time=new JTextField("09:00"),doctor=new JTextField(),reason=new JTextField();
        JComboBox<String> patient=patientCombo();JButton add=new JButton("Schedule appointment"),refresh=new JButton("Refresh");
        JPanel form=new JPanel(new GridLayout(2,5,8,6));form.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        for(String s:new String[]{"Patient","Date YYYY-MM-DD","Time HH:MM","Doctor","Reason"})form.add(new JLabel(s));
        form.add(patient);form.add(date);form.add(time);form.add(doctor);form.add(reason);
        JPanel p=new JPanel(new BorderLayout());p.add(form,BorderLayout.NORTH);p.add(new JScrollPane(new JTable(appointmentTable)),BorderLayout.CENTER);
        JPanel b=new JPanel(new FlowLayout(FlowLayout.LEFT));b.add(add);b.add(refresh);p.add(b,BorderLayout.SOUTH);
        add.addActionListener(e->run(()->{c.addAppointment((String)patient.getSelectedItem(),date.getText().trim(),time.getText().trim(),doctor.getText(),reason.getText());refreshAll();message("Appointment scheduled.");}));
        refresh.addActionListener(e->refreshAll());return p;
    }
    private JPanel billPanel(){
        JTextField date=new JTextField(LocalDate.now().toString()),description=new JTextField(),amount=new JTextField();
        JComboBox<String> patient=patientCombo(),status=new JComboBox<>(new String[]{"Unpaid","Paid","Partially paid","Waived"});
        JPanel form=new JPanel(new GridLayout(2,5,8,6));form.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        for(String s:new String[]{"Patient","Bill date YYYY-MM-DD","Description","Amount","Payment status"})form.add(new JLabel(s));
        form.add(patient);form.add(date);form.add(description);form.add(amount);form.add(status);
        JButton add=new JButton("Generate bill"),refresh=new JButton("Refresh");JPanel b=new JPanel(new FlowLayout(FlowLayout.LEFT));b.add(add);b.add(refresh);
        JPanel p=new JPanel(new BorderLayout());p.add(form,BorderLayout.NORTH);p.add(new JScrollPane(new JTable(billTable)),BorderLayout.CENTER);p.add(b,BorderLayout.SOUTH);
        add.addActionListener(e->run(()->{c.addBill((String)patient.getSelectedItem(),date.getText().trim(),description.getText(),amount.getText().trim(),String.valueOf(status.getSelectedItem()));refreshAll();message("Bill generated.");}));
        refresh.addActionListener(e->refreshAll());return p;
    }
    private JPanel treatmentPanel(){
        JTextField date=new JTextField(LocalDate.now().toString()),diagnosis=new JTextField(),treatment=new JTextField(),notes=new JTextField();
        JComboBox<String> patient=patientCombo();JPanel form=new JPanel(new GridLayout(2,5,8,6));form.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        for(String s:new String[]{"Patient","Date YYYY-MM-DD","Diagnosis","Treatment","Notes"})form.add(new JLabel(s));
        form.add(patient);form.add(date);form.add(diagnosis);form.add(treatment);form.add(notes);
        JButton add=new JButton("Save treatment record"),refresh=new JButton("Refresh");JPanel b=new JPanel(new FlowLayout(FlowLayout.LEFT));b.add(add);b.add(refresh);
        JPanel p=new JPanel(new BorderLayout());p.add(form,BorderLayout.NORTH);p.add(new JScrollPane(new JTable(treatmentTable)),BorderLayout.CENTER);p.add(b,BorderLayout.SOUTH);
        add.addActionListener(e->run(()->{c.addTreatment((String)patient.getSelectedItem(),date.getText().trim(),diagnosis.getText(),treatment.getText(),notes.getText());refreshAll();message("Treatment record saved.");}));
        refresh.addActionListener(e->refreshAll());return p;
    }
    private JComboBox<String> patientCombo(){
        JComboBox<String> combo=new JComboBox<>();patientCombos.add(combo);
        try{for(Patient p:c.patients())combo.addItem(p.getId()+" - "+p.getName());}catch(Exception ex){showError(ex);}return combo;
    }
    private void refreshAll(){
        try{
            List<Patient> currentPatients=c.patients();
            for(JComboBox<String> combo:patientCombos){
                Object selected=combo.getSelectedItem();
                combo.removeAllItems();
                for(Patient p:currentPatients)combo.addItem(p.getId()+" - "+p.getName());
                if(selected!=null)combo.setSelectedItem(selected);
            }
            patientTable.setRowCount(0);for(Patient p:currentPatients)patientTable.addRow(new Object[]{p.getId(),p.getName(),p.getAge(),p.getGender(),p.getPhone(),p.getDisease()});
            appointmentTable.setRowCount(0);for(Appointment a:c.appointments())appointmentTable.addRow(new Object[]{a.id(),a.patientName(),a.date(),a.time(),a.doctor(),a.reason(),a.status()});
            billTable.setRowCount(0);for(Bill b:c.bills())billTable.addRow(new Object[]{b.id(),b.patientName(),b.date(),b.description(),String.format("%.2f",b.amount()),b.paymentStatus()});
            treatmentTable.setRowCount(0);for(TreatmentRecord t:c.treatments())treatmentTable.addRow(new Object[]{t.id(),t.patientName(),t.date(),t.diagnosis(),t.treatment(),t.notes()});
        }catch(Exception ex){showError(ex);}
    }
    private void clearPatient(){selectedPatientId=0;name.setText("");age.setText("");phone.setText("");disease.setText("");gender.setSelectedIndex(0);patientsGrid.clearSelection();}
    private void run(ThrowingAction action){try{action.run();}catch(Exception ex){showError(ex);}}
    private void showError(Exception ex){JOptionPane.showMessageDialog(this,ex.getMessage(),"Operation failed",JOptionPane.ERROR_MESSAGE);}
    private void message(String s){JOptionPane.showMessageDialog(this,s,"Success",JOptionPane.INFORMATION_MESSAGE);}
    private static DefaultTableModel model(String... cols){return new DefaultTableModel(cols,0){@Override public boolean isCellEditable(int r,int c){return false;}};}
    @FunctionalInterface private interface ThrowingAction{void run()throws Exception;}
}
