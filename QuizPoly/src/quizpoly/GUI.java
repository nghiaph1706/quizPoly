
package quizpoly;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import static quizpoly.QuizPoly.api;
import static quizpoly.QuizPoly.list_correct;
import static quizpoly.QuizPoly.list_quest;

public class GUI extends javax.swing.JFrame {
    
    public String courseName, Content, Path;
    public NetworkDAO networkDAO;
    

    public GUI() throws IOException, Exception {
        initComponents();
        setLocationRelativeTo(this);
        btnStart.setEnabled(false);
    }
    
    private List<Questions> getAllQuestionsInPage() throws IOException{
        List<Questions> list_quest = new ArrayList<>();
        
        String content = new String(Files.readAllBytes(Paths.get(Path)));
        Document document = Jsoup.parse(content);
        Elements elmsPolyBody = document.getElementsByClass("poly-body");
        Elements elmsCourseName = document.getElementsByClass("course-name");
        courseName = elmsCourseName.get(0).text();
        for (int i = 0; i < elmsPolyBody.size(); i++) {
            int quesNum = i+1;
            String quesCont = elmsPolyBody.get(i).text();
            try {
                Elements elmsPolyAudio = elmsPolyBody.get(i).getElementsByClass("poly-audio");
                String quesAudio ="\n"+ elmsPolyAudio.get(0).getElementsByTag("audio").toString();
                quesCont+=quesAudio;
            } catch (Exception e) {
            }
            try {
                Elements elmsPolyImage = elmsPolyBody.get(i).getElementsByClass("poly-img");
                String quesImage ="\n"+ elmsPolyImage.get(0).getElementsByTag("img").toString();
                quesCont+=quesImage;
            } catch (Exception e) {
            }
            Questions questions = new Questions(quesNum, quesCont);
            list_quest.add(questions);
        }
        return list_quest;
    }
    
    private String enCoderUrl(String courseName){
        try {
            courseName.replaceAll(" ", "%20");
            if (courseName.contains("(")) {
                String part2 =courseName.substring(courseName.indexOf("("),courseName.indexOf(")"))+")";
                String part1 = courseName.replace(part2, "");
                part1 = URLEncoder.encode(part1, "UTF-8");
                String url = api.concat(part1.concat(part2));
            }
            String url = api.concat(URLEncoder.encode(courseName, "UTF-8"));
            return url.replace("+", "%20");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Không thể enCodeUrl");
            return null;
        }
    }
    
    private List<AnswerCorrect> getAnswerCorrect(String url) throws IOException, ParseException, Exception{
        List<AnswerCorrect> list_correct = new ArrayList<>();
        
        String rawJson = networkDAO.request(url);
        
        JSONParser parser = new JSONParser();

        JSONArray data = (JSONArray) parser.parse(rawJson);
        
        for (int i = 0; i < data.size(); i++) {
            JSONObject jsdt = (JSONObject) data.get(i);
            String q = (String) jsdt.get("q");
            List<String> list_AnsCor = new ArrayList<>();
            try {
                String a = (String) jsdt.get("a");
                list_AnsCor.add(a);
            } catch (Exception e) {
                JSONArray aObj = (JSONArray) jsdt.get("a");
                for (int j = 0; j < aObj.size(); j++) {
                    String AnsCor = (String) aObj.get(j);
                    list_AnsCor.add(AnsCor);
                }
            }
            
            AnswerCorrect answerCorrect = new AnswerCorrect(q, list_AnsCor);
            list_correct.add(answerCorrect);
        }
        return list_correct;
    }
    
    private void showAllQuestionsAnswerCorrect(List<Questions> list_quest, List<AnswerCorrect> list_correct){
        Content = "";
        for (Questions questions : list_quest) {
            Content+="Question "+questions.getQuesNum()+":\n"+questions.getQuesCont();
            
            for (AnswerCorrect answerCorrect : list_correct) {
                if (questions.getQuesCont().replaceAll("\\W","").equals(answerCorrect.getQuesCont().replaceAll("\\W",""))) {
                    Content+="\nCORRECT ANSWER: \n";
                    for (String ansCor : answerCorrect.getList_AnsCor()) {
                        Content+=ansCor+"\n";
                    }
                    Content+="\n***************************************\n";
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtPath = new javax.swing.JTextField();
        btnChoose = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtArea = new javax.swing.JTextArea();
        btnStart = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtPath.setEditable(false);

        btnChoose.setText("Choose Quiz");
        btnChoose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseActionPerformed(evt);
            }
        });

        txtArea.setColumns(20);
        txtArea.setRows(5);
        jScrollPane1.setViewportView(txtArea);

        btnStart.setText("Start");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtPath, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnStart, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 592, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtPath)
                    .addComponent(btnChoose, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(btnStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 763, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnChooseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseActionPerformed
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Quiz.txt", "txt");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            Path = chooser.getSelectedFile().getPath();
            txtPath.setText(Path);
            btnStart.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đường dẫn.");
        }
    }//GEN-LAST:event_btnChooseActionPerformed

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        try {
            list_quest = getAllQuestionsInPage();
            list_correct = getAnswerCorrect(enCoderUrl(courseName));
            showAllQuestionsAnswerCorrect(list_quest, list_correct);
            txtArea.setText(Content);
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnStartActionPerformed

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
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new GUI().setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChoose;
    private javax.swing.JButton btnStart;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtArea;
    private javax.swing.JTextField txtPath;
    // End of variables declaration//GEN-END:variables
}
