package com.knf.dev.librarymanagementsystem.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public interface FileService {
	public void exportCSV(String fileName, HttpServletResponse response)
			throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException;
}
