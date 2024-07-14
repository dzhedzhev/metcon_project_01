package com.containerdepot.metcon.model.enums;

public enum ContainerIsoType { /*TODO fix if needed*/
    TWENTY_FOOT_DV("22G1"),
    TWENTY_FOOT_FL("22P1"),
    TWENTY_FOOT_HC("25G1"),
    TWENTY_FOOT_OT("22U1"),
    TWENTY_FOOT_RF("22R1"),
    TWENTY_FOOT_TK("22T1"),
    FORTY_FOOT_DV("42G1"),
    FORTY_FOOT_FL("42P1"),
    FORTY_FOOT_FL_HC("45P1"),
    FORTY_FOOT_RF("42R1"),
    FORTY_FOOT_RF_HC("45R1"),
    FORTY_FOOT_HC("45G1"),
    FORTY_FOOT_OT("42U1"),
    FORTY_FOOT_OT_HC("45U1"),
    FORTY_FOOT_TK("42T1"),
    FORTY_FIVE_FOOT_HC("45G1");

    private final String type;

    private ContainerIsoType(final String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
}
