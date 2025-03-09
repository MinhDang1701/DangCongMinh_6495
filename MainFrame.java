package novelreadergui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Create graphic GUI
 */
public class MainFrame extends JFrame {

    private StoryManager storyManager;
    private JList<Story> storyList;
    private DefaultListModel<Story> listModel;

    public MainFrame() {
        storyManager = new StoryManager(); 

        setTitle("Story Manager");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        updateStoryList();
        storyList = new JList<>(listModel);
        storyList.setFont(new Font("Arial", Font.PLAIN, 16));

        // Double-click 
        storyList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Double click 
                    int index = storyList.getSelectedIndex();
                    if (index != -1) {
                        Story selectedStory = listModel.get(index);
                        JOptionPane.showMessageDialog(
                                MainFrame.this,
                                new JScrollPane(new JTextArea(selectedStory.getContent(), 10, 30)),
                                "Story Content - " + selectedStory.getTitle(),
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                }
            }
        });

        JButton addButton = new JButton("Add");
        addButton.addActionListener((ActionEvent e) -> {
            String title = JOptionPane.showInputDialog("Title:");
            String author = JOptionPane.showInputDialog("Author:");
            String genre = JOptionPane.showInputDialog("Genre:");
            String content = showMultiLineInputDialog("Enter Content:");

            if (title != null && author != null && genre != null && content != null) {
                Story story = new Story(title, author, genre, content);
                storyManager.addStory(story);
                updateStoryList();
            }
        });

        JButton editButton = new JButton("Edit");
        editButton.addActionListener((ActionEvent e) -> {
            int index = storyList.getSelectedIndex();
            if (index != -1) {
                Story oldStory = listModel.get(index);
                String title = JOptionPane.showInputDialog("New Title:", oldStory.getTitle());
                String author = JOptionPane.showInputDialog("New Author:", oldStory.getAuthor());
                String genre = JOptionPane.showInputDialog("New Genre:", oldStory.getGenre());
                String content = showMultiLineInputDialog("Edit Content:", oldStory.getContent());

                if (title != null && author != null && genre != null && content != null) {
                    Story updatedStory = new Story(title, author, genre, content);
                    storyManager.editStory(index, updatedStory);
                    updateStoryList();
                }
            }
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener((ActionEvent e) -> {
            int index = storyList.getSelectedIndex();
            if (index != -1) {
                storyManager.deleteStory(index);
                updateStoryList();
            }
        });

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener((ActionEvent e) -> {
            String keyword = JOptionPane.showInputDialog("Enter Title:");
            if (keyword != null) {
                List<Story> results = storyManager.searchStories(keyword);
                listModel.clear();
                for (Story story : results) {
                    listModel.addElement(story);
                }
            }
        });

        JPanel panel = new JPanel();
        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);
        panel.add(searchButton);

        add(new JScrollPane(storyList), BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
    }

    private void updateStoryList() {
        listModel.clear();
        for (Story story : storyManager.getStoryList()) {
            listModel.addElement(story);
        }
    }

    private String showMultiLineInputDialog(String message) {
        return showMultiLineInputDialog(message, "");
    }

    private String showMultiLineInputDialog(String message, String defaultText) {
        JTextArea textArea = new JTextArea(10, 30);
        textArea.setText(defaultText);
        JScrollPane scrollPane = new JScrollPane(textArea);
        int option = JOptionPane.showConfirmDialog(null, scrollPane, message, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        return (option == JOptionPane.OK_OPTION) ? textArea.getText() : null;
    }

    // Run File
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
