package org.ripreal.textclassifier2.textreaders;

import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ripreal.textclassifier2.CharacteristicUtils;
import org.ripreal.textclassifier2.actions.ClassifierAction;
import org.ripreal.textclassifier2.actions.ClassifierEventsDispatcher;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicFactory;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.ClassifiableText;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@AllArgsConstructor
public class ExcelFileReader extends ClassifierEventsDispatcher implements ClassifiableReader  {

    private File file;
    private int sheetNumber;
    private CharacteristicFactory characteristicFactory;
    private final List<ClassifierAction> listeners = new ArrayList<>();

    public List<ClassifiableText> toClassifiableTexts() {

        if (!file.exists() ||
                sheetNumber < 1) {
            throw new IllegalArgumentException("File with texts not exist or has wrong format!");
        }

        try (XSSFWorkbook excelFile = new XSSFWorkbook(new FileInputStream(file))) {
            XSSFSheet sheet = excelFile.getSheetAt(sheetNumber - 1);

            // at least two rows
            if (sheet.getLastRowNum() > 0) {
                return getClassifiableTexts(sheet);
            } else {
                dispatch("Excel sheet (#" + sheetNumber + ") is empty");
            }
        } catch (IllegalArgumentException e) {
            dispatch("Excel sheet (#" + sheetNumber + ") is not found");
        } catch (IOException e) {
            dispatch(e.getMessage());
        }
        return new ArrayList<>();
    }

    private List<ClassifiableText> getClassifiableTexts(XSSFSheet sheet) {
        List<Characteristic> characteristics = getCharacteristics(sheet);
        List<ClassifiableText> classifiableTexts = new ArrayList<>();

        // start from second row
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Map<Characteristic, CharacteristicValue> characteristicsValues = getCharacteristicsValues(sheet.getRow(i), characteristics);

            // exclude empty rows
            if (!sheet.getRow(i).getCell(0).getStringCellValue().equals("")) {
                classifiableTexts.add(characteristicFactory.newClassifiableText(sheet.getRow(i).getCell(0).getStringCellValue(), characteristicsValues));
            }
        }

        //todo: now it's error prone approach. Value's order and parent should be filled somewhere ese.
        for (Characteristic characteristic : characteristics) {
            int i = 1;
            for (CharacteristicValue characteristicValue : characteristic.getPossibleValues()) {
                characteristicValue.setCharacteristic(characteristic);
                characteristicValue.setOrderNumber(i++);
            }
        }

        return classifiableTexts;
    }

    private Map<Characteristic, CharacteristicValue> getCharacteristicsValues(Row row, List<Characteristic> characteristics) {
        Map<Characteristic, CharacteristicValue> characteristicsValues = new HashMap<>();

        for (int i = 1; i < row.getLastCellNum(); i++) {
            Characteristic characteristic = characteristics.get(i - 1);
            String valueName = row.getCell(i).getStringCellValue();

            CharacteristicValue value = CharacteristicUtils.findByValue(
                characteristic.getPossibleValues(), valueName, characteristicFactory::newCharacteristicValue);
            if (value == null) {
                value = characteristicFactory.newCharacteristicValue(valueName);
            }
            characteristic.addPossibleValue(value);
            characteristicsValues.put(characteristic, value);
        }

        return characteristicsValues;
    }

    private List<Characteristic> getCharacteristics(XSSFSheet sheet) {
        List<Characteristic> characteristics = new ArrayList<>();

        // first row from second to last columns contains Characteristics names
        for (int i = 1; i < sheet.getRow(0).getLastCellNum(); i++) {
            characteristics.add(characteristicFactory.newCharacteristic(sheet.getRow(0).getCell(i).getStringCellValue()));
        }

        return characteristics;
    }

    @Override
    public void subscribe(ClassifierAction action) {
        listeners.add(action);
    }

    @Override
    public void dispatch(String text) {
        listeners.forEach(action -> action.dispatch(ClassifierAction.EventTypes.EXCEL_FILE_READER_EVENT, text));
    }
}