package org.fasttrackit.healthcare.transfer.profile;

public class GetProfilesRequest {
    private String partialEmail;

    public String getPartialEmail() {
        return partialEmail;
    }

    public void setPartialEmail(String partialEmail) {
        this.partialEmail = partialEmail;
    }

    @Override
    public String toString() {
        return "GetProfilesRequest{" +
                "partialEmail='" + partialEmail + '\'' +
                '}';
    }
}
