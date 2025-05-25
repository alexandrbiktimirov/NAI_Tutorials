# NAI_Tutorials
Projects for a second-year subject in PJAIT - Artificial Intelligence Tools.

## 📁 Structure
Each task is located under its own package inside the src/main/java/org/example/ (or org.example) directory:

- `KNN/` - package for K-Nearest Neighbors mini-programming project.
- `Perceptron/` - package for Perceptron mini-programming project.
- `SingleLayerNeuralNetwork` - package for Single-Layer Neural Network mini-programming project.
- `KMeans` - package for K-Means clustering mini-programming project.
- `Bayes` - package for Naive Bayes classifier mini-programming project.
- `BruteForce` - package for Brute Force mimi-programming project.

## 🔍 Mini-programming projects covered
**Mini-programming project 1: KNN (K-Nearest Neighbors)**

- Implements a basic KNN classifier with a custom sorting routine and tie-breaking logic.
- Demonstrates multi-class classification on the Iris dataset.
- Explores how changing k affects prediction accuracy.

**Mini-programming project 2: Perceptron**

- Implements the Delta Rule (gradient-based) for a single-layer perceptron.
- Demonstrates binary classification on a 2D subset of the Iris dataset.
- Shows how to plot the decision boundary in ASCII or export boundary points to a CSV file.

**Mini-programming project 3: Single-Layer Neural Network**

- Implements a basic one-vs-rest neural architecture using multiple perceptrons.
- Designed for multi-class classification of text-based language detection tasks.
- Each perceptron is trained to recognize one language by distinguishing it from others.
- Supports text vectorization through normalized letter-frequency mapping (a–z).
- Demonstrates prediction on both vectorized inputs and raw user-provided text.

**Mini-programming project 4: Naive Bayes Classifier**

- Implements a categorical Naive Bayes classifier with optional Laplace smoothing.
- Precomputes prior and conditional probabilities during training.
- Classifies new data and evaluates results using accuracy, precision, recall, and F-measure.

**Mini-programming project 5: K-Means Clustering**

- Implements the K-Means algorithm for unsupervised clustering on the Iris dataset.
- Randomly initializes cluster assignments and iteratively updates centroids until convergence.
- Calculates Within-Cluster Sum of Squares (WCSS) to evaluate compactness.
- Visualizes clusters and centroids across all attribute pairs using scatter plots.

**Mini-programming project 6: Knapsack problem (Brute Force and Greedy)**

- Implements two approaches to the Knapsack problem: exhaustive brute-force search and a greedy density-based heuristic.
- Evaluates all feasible item combinations using binary masks to find the optimal solution.
- Compares results and execution time of both methods through a terminal interface.

## 🚀 Getting Started

1. Clone or Download this repository.
2. Open in Your IDE (e.g., IntelliJ, Eclipse) or build from the command line using Maven.
3. Run any of the tasks:
- Main Class: org.ml.Main (or another entry point, depending on your structure).
- The console prompts will guide you to choose a classifier, input vectors, etc.
4. Plotting:
- Some tasks use ASCII plots for quick visualization.
- Boundary points can also be exported to CSV and viewed in Excel, Python (matplotlib), or another plotting tool.

## 🛠️ Requirements
- Java 17+ (tested with Java 17, should work with newer versions)
- Maven (for dependency management and building)
- No external libraries for core tasks (except optional libraries for advanced plotting)

## 📄 License
This project is open-source and available under the MIT License. Feel free to modify, distribute, and use it for your own machine learning experiments.
