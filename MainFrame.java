package novelreadergui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
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
        for (Story story : storyManager.getStoryList()) {
            listModel.addElement(story);
        }
        storyList = new JList<>(listModel);
        storyList.setFont(new Font("Arial", Font.PLAIN, 16));

        JButton addButton = new JButton("Add");
        addButton.addActionListener((ActionEvent e) -> {
            String title = JOptionPane.showInputDialog("Title:");
            String author = JOptionPane.showInputDialog("Author:");
            String genre = JOptionPane.showInputDialog("Genre:");
            String content = JOptionPane.showInputDialog("Content:");
            if (title != null && author != null && genre != null && content != null) {
                Story story = new Story(title, author, genre, content);
                storyManager.addStory(story);
                listModel.addElement(story);
            }
        });

        JButton editButton = new JButton("Edit");
        editButton.addActionListener((ActionEvent e) -> {
            int index = storyList.getSelectedIndex();
            if (index != -1) {
                Story oldStory = storyManager.getStoryList().get(index);
                String title = JOptionPane.showInputDialog("New Title:", oldStory.getTitle());
                String author = JOptionPane.showInputDialog("New Author:", oldStory.getAuthor());
                String genre = JOptionPane.showInputDialog("New Genre:", oldStory.getGenre());
                String content = JOptionPane.showInputDialog("New Content:", oldStory.getContent());

                if (title != null && author != null && genre != null && content != null) {
                    Story updatedStory = new Story(title, author, genre, content);
                    storyManager.editStory(index, updatedStory);
                    listModel.set(index, updatedStory);
                }
            }
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener((ActionEvent e) -> {
            int index = storyList.getSelectedIndex();
            if (index != -1) {
                storyManager.deleteStory(index);
                listModel.remove(index);
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
//run file

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
