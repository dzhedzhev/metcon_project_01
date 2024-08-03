package com.containerdepot.metcon.model.enums;

public enum ContainerIsoType { /*TODO fix if needed*/
    TWENTY_FT_DV("22G1"),
    TWENTY_FT_FL("22P1"),
    TWENTY_FT_HC("25G1"),
    TWENTY_FT_OT("22U1"),
    TWENTY_FT_RF("22R1"),
    TWENTY_FT_TK("22T1"),
    FORTY_FT_DV("42G1"),
    FORTY_FT_FL("42P1"),
    FORTY_FT_FL_HC("45P1"),
    FORTY_FT_RF("42R1"),
    FORTY_FT_RF_HC("45R1"),
    FORTY_FT_HC("45G1"),
    FORTY_FT_OT("42U1"),
    FORTY_FT_OT_HC("45U1"),
    FORTY_FT_TK("42T1"),
    FORTY_FIVE_FT_HC("45G1");

    private final String type;

    private ContainerIsoType(final String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
}
