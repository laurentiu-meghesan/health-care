package org.fasttrackit.healthcare.transfer.patient;

public class GetPatientsRequest {
    private String partialFirstName;
    private String partialLastName;

    public String getPartialFirstName() {
        return partialFirstName;
    }

    public void setPartialFirstName(String partialFirstName) {
        this.partialFirstName = partialFirstName;
    }

    public String getPartialLastName() {
        return partialLastName;
    }

    public void setPartialLastName(String partialLastName) {
        this.partialLastName = partialLastName;
    }

    @Override
    public String toString() {
        return "GetPatientsRequest{" +
                "partialFirstName='" + partialFirstName + '\'' +
                ", partialLastName='" + partialLastName + '\'' +
                '}';
    }
}
