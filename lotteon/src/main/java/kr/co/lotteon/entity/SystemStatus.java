package kr.co.lotteon.entity;

public enum SystemStatus {
    OPERATING("[운영중]", "green"),
    READY("[운영준비]", "blue"),
    STOPPED("[운영중지]", "red");

    private final String operationText;
    private final String statusClass;

    SystemStatus(String operationText, String statusClass) {
        this.operationText = operationText;
        this.statusClass = statusClass;
    }

    public String getOperationText() {
        return operationText;
    }

    public String getStatusClass() {
        return statusClass;
    }
}
