package utils;

/**
 * This class contains all the constants used globally throughout the project
 */
public class Constants {
    
    public static String SHAPES_NAMESPACE = "http://shaclshapes.org/";
    public static String MEMBERSHIP_GRAPH_ROOT_NODE = "<http://www.schema.hng.root> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.schema.hng.root#HNG_Root> .";
    public static String RDF_TYPE = "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>";
    public static String INSTANCE_OF = "<http://www.wikidata.org/prop/direct/P31>";
    public static String SUB_CLASS_OF = "<http://www.w3.org/2000/01/rdf-schema#subClassOf>";
    
    
    public static String RUNTIME_LOGS = ConfigManager.getProperty("output_file_path") + ConfigManager.getProperty("dataset_name") + "_RUNTIME_LOGS.csv";
    public static String SAMPLING_LOGS = ConfigManager.getProperty("output_file_path") + ConfigManager.getProperty("dataset_name") + "_SAMPLING_LOGS.csv";
    public static String CLEAN_DATASET_FILE = ConfigManager.getProperty("output_file_path") + ConfigManager.getProperty("dataset_name") + "_CLEAN_DATASET_FILE.n3";
    public static String DIRTY_DATASET_FILE = ConfigManager.getProperty("output_file_path") + ConfigManager.getProperty("dataset_name") + "_DIRTY_DATASET_FILE.n3";
    
}
