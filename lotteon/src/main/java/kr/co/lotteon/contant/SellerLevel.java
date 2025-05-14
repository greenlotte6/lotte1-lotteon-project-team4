package kr.co.lotteon.contant;

public enum SellerLevel {
    BASIC("기본"), PREMIUM("프리미엄"), POWER("파워"), ADMIN("관리자");

    private final String label;
    SellerLevel(String label) { this.label = label; }
    public String getLabel() { return label; }
}


