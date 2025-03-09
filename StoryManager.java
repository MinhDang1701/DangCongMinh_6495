package novelreadergui;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the list of stories, including saving, loading, and searching.
 */
public class StoryManager {

    private List<Story> storyList = new ArrayList<>();
    private File file = new File("stories.txt");
    private final String DELIMITER = " | ";

    public StoryManager() {
        readFromFile();
    }

    public void addStory(Story story) {
        storyList.add(story);
        saveToFile();
    }

    public void deleteStory(int index) {
        if (index >= 0 && index < storyList.size()) {
            storyList.remove(index);
            saveToFile();
        }
    }

    public void editStory(int index, Story updatedStory) {
        if (index >= 0 && index < storyList.size()) {
            storyList.set(index, updatedStory);
            saveToFile();
        }
    }

    public List<Story> searchStories(String keyword) {
        List<Story> results = new ArrayList<>();
        for (Story story : storyList) {
            if (story.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(story);
            }
        }
        return results;
    }

    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            for (Story story : storyList) {
                writer.write(story.getTitle() + DELIMITER
                        + story.getAuthor() + DELIMITER
                        + story.getGenre() + DELIMITER
                        + story.getContent().replace("\n", "<br>"));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFromFile() {
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\s\\|\\s", 4);
                    if (parts.length == 4) {
                        Story story = new Story(parts[0], parts[1], parts[2], parts[3].replace("<br>", "\n"));
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
