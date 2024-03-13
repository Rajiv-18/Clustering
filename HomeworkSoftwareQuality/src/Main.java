import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.Cobweb;
import net.sf.javaml.clustering.FarthestFirst;
import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.clustering.evaluation.ClusterEvaluation;
import net.sf.javaml.clustering.evaluation.SumOfSquaredErrors;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.data.FileHandler;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            Dataset data = FileHandler.loadDataset(new File("iris.data"), 4, ",");
            // calls out different methods in main class to perform calculations
            double kMeansScore = clusteringPerformed(new KMeans(4), data);
            double farthestFirstScore = clusteringPerformed(new FarthestFirst(), data);
            double cobwebScore = performCobweb(data);

            // To Print out Scores
            System.out.println("Score of KMeans: " + kMeansScore);
            System.out.println("Score of Farthest First: " + farthestFirstScore);
            System.out.println("Score of Cobweb: " + cobwebScore);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static double clusteringPerformed(Clusterer clusterer, Dataset data) {

        long startTime = System.currentTimeMillis();
        Dataset[] clusters = clusterer.cluster(data);
        long endTime = System.currentTimeMillis();

        return evaluateCluster(clusters);
    }

    private static double performCobweb(Dataset data) {
        Clusterer cobweb = new Cobweb();
        Dataset[] clusters = cobweb.cluster(data);
        return evaluateCluster(clusters);
    }

    private static double evaluateCluster(Dataset[] clusters) {
        ClusterEvaluation sse = new SumOfSquaredErrors();
        return sse.score(clusters);
    }
}
