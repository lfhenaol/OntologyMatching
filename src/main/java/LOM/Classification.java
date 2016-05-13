package LOM;

import Ontology.Ontology;
import argo.jdom.JsonNode;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.OWL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lucho on 18/04/2016.
 */
public class Classification {
    private Resource purpose;
    private TaxonPath taxonPath;
    private Resource description;
    private Resource keyword;
    private OntModel classificationModel;

    Classification(JsonNode jsonClassification){
        this.classificationModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

        this.purpose = this.undefinedToResource(jsonClassification.getNode("purpose").getText());
        this.description = this.undefinedToResource(jsonClassification.getNode("description").getText());
        this.keyword = this.undefinedToResource(jsonClassification.getNode("keyword").getText());

        this.taxonPath = new TaxonPath(jsonClassification.getNode("taxonPath"));

    }

    public Map match(Ontology onto){
        ArrayList<Resource> localProp = new ArrayList<>();
        localProp.add(this.purpose);
        localProp.add(this.description);
        localProp.add(this.keyword);

        ArrayList<String> jresponse = new ArrayList<>();
        jresponse.add("purpose");
        jresponse.add("description");
        jresponse.add("keyword");

        String json = "{\n";
        Map similar = onto.getSimilarity(localProp);
        for (int i = 0; i<localProp.size();i++) {
            Resource rs = (Resource) similar.get(localProp.get(i));
            Resource lc = localProp.get(i);
            this.classificationModel.createObjectProperty(rs.toString());
            this.classificationModel.createObjectProperty(lc.toString());
            this.classificationModel.createSymmetricProperty(lc.toString());
            this.classificationModel.add(lc, OWL.equivalentProperty, rs);
            json = json + "\""+jresponse.get(i)+"\": \"" + ((lc.getLocalName().equals("undefined"))? "Unselected":((rs.getLocalName().equals("No-Matches"))? "No matches": rs.toString()))+"\"";
            if(i < (localProp.size()-1)){
                json = json + ",";
            }
        }

        Map result = this.taxonPath.match(onto);
        this.classificationModel.union(this.taxonPath.getTaxonPathModel());
        json = json +",\"taxonPath\":" +result.get("jsonObject").toString() + "}";

        Map<String,Object> response = new HashMap<>();
        response.put("model",this.classificationModel);
        response.put("jsonObject",json);
        return response;
    }

    private Resource undefinedToResource(String localProperty){
        String uri = "http://www.example.com/undefined";
        return (localProperty.equals("undefined"))? this.classificationModel.createResource(uri) : this.classificationModel.createResource(localProperty);
    }

    public Resource getPurpose() {
        return purpose;
    }

    public void setPurpose(Resource purpose) {
        this.purpose = purpose;
    }

    public TaxonPath getTaxonPath() {
        return taxonPath;
    }

    public void setTaxonPath(TaxonPath taxonPath) {
        this.taxonPath = taxonPath;
    }

    public Resource getDescription() {
        return description;
    }

    public void setDescription(Resource description) {
        this.description = description;
    }

    public Resource getKeyword() {
        return keyword;
    }

    public void setKeyword(Resource keyword) {
        this.keyword = keyword;
    }

    public OntModel getClassificationModel() {
        return classificationModel;
    }

    public void setClassification(OntModel classification) {
        this.classificationModel = classification;
    }
}
