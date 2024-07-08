import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Fakenewsdetection {

    public static void main(String[] args) {
        // Generate synthetic news articles
        List<String> newsArticles = generateNewsArticles(50); // Generate 50 articles

        // Label the news articles as real or fake based on predefined criteria
        List<Boolean> labels = labelNewsArticles(newsArticles);

        // Write the synthetic dataset to a CSV file
        writeDatasetToCSV(newsArticles, labels, "synthetic_dataset.csv");
    }

    // Method to generate synthetic news articles
    private static List<String> generateNewsArticles(int numArticles) {
        List<String> newsArticles = new ArrayList<>();
        Random random = new Random();
        String[] topics = {"politics", "health", "technology", "entertainment", "sports"};
        String[] templates = {
                "New Study Reveals Surprising Health Benefits of {0}!",
                "{0} Government Announces New Policy Changes",
                "Breaking: Major {0} Company Launches Revolutionary Product",
                "Controversy Erupts Over {0} Star's Latest Comments",
                "Exclusive Interview with {0} on Their Latest Project"
        };

        for (int i = 0; i < numArticles; i++) {
            String topic = topics[random.nextInt(topics.length)];
            String template = templates[random.nextInt(templates.length)];
            String article = template.replace("{0}", topic);
            newsArticles.add(article);
        }

        return newsArticles;
    }

    // Method to label news articles as real or fake based on predefined criteria
    private static List<Boolean> labelNewsArticles(List<String> newsArticles) {
        List<Boolean> labels = new ArrayList<>();
        Random random = new Random();

        for (String article : newsArticles) {
            // Apply predefined criteria to determine if the article is real or fake
            // For simplicity, let's randomly assign labels
            boolean isReal = random.nextBoolean();
            labels.add(isReal);
        }

        return labels;
    }

    // Method to write the synthetic dataset to a CSV file
    private static void writeDatasetToCSV(List<String> newsArticles, List<Boolean> labels, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("Article,Label\n");
            for (int i = 0; i < newsArticles.size(); i++) {
                String article = newsArticles.get(i).replace(",", ";"); // Replace commas to avoid CSV format issues
                String label = labels.get(i) ? "REAL" : "FAKE";
                writer.write("\"" + article + "\"," + label + "\n");
            }
            System.out.println("Dataset written to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}