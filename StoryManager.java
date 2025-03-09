/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package novelreadergui;

/**
 *
 * @author ThinkPad
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StoryManager {

    private List<Story> storyList = new ArrayList<>();
    private File file = new File("stories.txt");

    public StoryManager() {
        readFromFile();
    }

    // Add new story
    public void addStory(Story story) {
        storyList.add(story);
        saveToFile();
    }

    // Delete 
    public void deleteStory(int index) {
        if (index >= 0 && index < storyList.size()) {
            storyList.remove(index);
            saveToFile();
        }
    }

    // Edit 
    public void editStory(int index, Story updatedStory) {
        if (index >= 0 && index < storyList.size()) {
            storyList.set(index, updatedStory);
            saveToFile();
        }
    }

    // Search stories by title
    public List<Story> searchStories(String keyword) {
        List<Story> results = new ArrayList<>();
        for (Story story : storyList) {
            if (story.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(story);
            }
        }
        return results;
    }

    // Save
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Story story : storyList) {
                writer.write(story.getTitle() + ","
                        + story.getAuthor() + ","
                        + story.getGenre() + ","
                        + story.getContent());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFromFile() {
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 4) {
                        Story story = new Story(parts[0], parts[1], parts[2], parts[3]);
                        storyList.add(story);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Story> getStoryList() {
        return storyList;
    }
}
