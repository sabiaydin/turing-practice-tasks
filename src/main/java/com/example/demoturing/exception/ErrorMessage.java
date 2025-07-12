package com.example.demoturing.exception;

public final class ErrorMessage {
    public static final String HOTEL_NOT_FOUND = "Otel tapılmadı";
    public static final String INVALID_FILE_FORMAT = "Yalnız .xlsx formatlı fayllara icazə verilir.";
    public static final String INVALID_EXCEL_DATA = "Excel faylında yalnış formatlı məlumat var.";
    public static final String FILE_PROCESSING_ERROR = "Excel faylını oxumaq mümkün olmadı.";
    public static final String INTERNAL_ERROR = "Gözlənilməz xəta baş verdi.";

    private ErrorMessage() {}
}
