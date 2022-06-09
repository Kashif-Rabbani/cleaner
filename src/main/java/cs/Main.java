package cs;

import utils.*;


public class Main {
    public static String configPath;
    public static String datasetPath;
    
    public static boolean extractMaxCardConstraints;
    
    public static void main(String[] args) throws Exception {
        configPath = args[0];
        datasetPath = ConfigManager.getProperty("dataset_path");
        
        System.out.println("Benchmark Initiated for " + ConfigManager.getProperty("dataset_path"));
        Utils.log("Dataset,Method,Second,Minute,SecondTotal,MinuteTotal,MaxCard,DatasetPath");
        Utils.getCurrentTimeStamp();
        try {
            //First Step -- find the line numbers of triples having invalid IRIs
            EntityValidator entityValidator = new EntityValidator(datasetPath);
            
            // 2nd Step -- use the identified line numbers to create two files, one without triples having invalid IRIs and one only having triples with invalid IRIs
            FilesUtil.removeLinesFromFile(datasetPath, entityValidator.getInvalidLineNumbers());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
