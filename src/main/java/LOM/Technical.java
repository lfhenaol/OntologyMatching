package LOM;

import argo.jdom.JsonNode;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;

/**
 * Created by Lucho on 18/04/2016.
 */
public class Technical {
    private Resource format;
    private Resource size;
    private Resource location;
    private Requirement requirement;
    private Resource installationRemarks;
    private Resource otherPlatformRequirements;
    private Resource duration;
    private OntModel technicalModel;

    Technical(JsonNode jsonTechnical){
        this.technicalModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        this.format = this.technicalModel.createResource(jsonTechnical.getNode("format").getText());
        this.size = this.technicalModel.createResource(jsonTechnical.getNode("size").getText());
        this.location = this.technicalModel.createResource(jsonTechnical.getNode("location").getText());
        this.requirement = new Requirement(jsonTechnical.getNode("requirement"));
        this.installationRemarks = this.technicalModel.createResource(jsonTechnical.getNode("installationRemarks").getText());
        this.otherPlatformRequirements = this.technicalModel.createResource(jsonTechnical.getNode("otherPlatformRequirements").getText());
        this.duration = this.technicalModel.createResource(jsonTechnical.getNode("duration").getText());

    }

    public Resource getFormat() {
        return format;
    }

    public void setFormat(Resource format) {
        this.format = format;
    }

    public Resource getSize() {
        return size;
    }

    public void setSize(Resource size) {
        this.size = size;
    }

    public Resource getLocation() {
        return location;
    }

    public void setLocation(Resource location) {
        this.location = location;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    public void setRequirement(Requirement requirement) {
        this.requirement = requirement;
    }

    public Resource getInstallationRemarks() {
        return installationRemarks;
    }

    public void setInstallationRemarks(Resource installationRemarks) {
        this.installationRemarks = installationRemarks;
    }

    public Resource getOtherPlatformRequirements() {
        return otherPlatformRequirements;
    }

    public void setOtherPlatformRequirements(Resource otherPlatformRequirements) {
        this.otherPlatformRequirements = otherPlatformRequirements;
    }

    public Resource getDuration() {
        return duration;
    }

    public void setDuration(Resource duration) {
        this.duration = duration;
    }

    public OntModel getTechnicalModel() {
        return technicalModel;
    }

    public void setTechnicalModel(OntModel technical) {
        this.technicalModel = technical;
    }
}
