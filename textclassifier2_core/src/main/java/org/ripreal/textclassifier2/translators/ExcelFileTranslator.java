package org.ripreal.textclassifier2.translators;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ripreal.textclassifier2.CharacteristicUtils;
import org.ripreal.textclassifier2.model.*;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.ripreal.textclassifier2.ngram.VocabularyBuilder;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class ExcelFileTranslator implements ClassifiableTranslator {

    @NonNull
    private final File file;
    private final int sheetNumber;
    @NonNull
    private final ClassifiableFactory characteristicFactory;
    @NonNull
    private List<ClassifiableText> cached_classifiableText = new ArrayList<>();

    @Override
    public ClassifiableFactory getCharacteristicFactory() {
        return characteristicFactory;
    }

    @Override
    public List<ClassifiableText> toClassifiableTexts() {

        if (!cached_classifiableText.isEmpty()) {
            return cached_classifiableText;
        }

        if (!file.exists() ||
                sheetNumber < 1) {
            log.info(String.format("Excel file with path %s not exist or has wrong format!", file.getAbsolutePath()));
            return cached_classifiableText;
        }

        try (XSSFWorkbook excelFile = new XSSFWorkbook(new FileInputStream(file))) {
            XSSFSheet sheet = excelFile.getSheetAt(sheetNumber - 1);

            // at least two rows
            if (sheet.getLastRowNum() > 0) {
                cached_classifiableText = getClassifiableTexts(sheet);
            } else {
                log.info("Excel sheet (#" + sheetNumber + ") is empty");
            }
        } catch (IllegalArgumentException e) {
            log.info("Excel sheet (#" + sheetNumber + ") is not found");
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        return cached_classifiableText;
    }

    @Override
    public Set<Characteristic> toCharacteristics() {
        return toClassifiableTexts().stream()
                .flatMap(text -> text.getCharacteristics().stream())
                .map(CharacteristicValue::getCharacteristic)
                .distinct()
                .collect(Collectors.toSet());
    }

    @Override
    public List<VocabularyWord> toVocabulary(NGramStrategy ngram) {
        return new VocabularyBuilder(ngram).getVocabulary(toClassifiableTexts(), getCharacteristicFactory());
    }

    @Override
    public void reset() {
        cached_classifiableText = new ArrayList<>();
    }

    private List<ClassifiableText> getClassifiableTexts(XSSFSheet sheet) {
        List<Characteristic> characteristics = getCharacteristics(sheet);
        List<ClassifiableText> classifiableTexts = new ArrayList<>();

        // start from second row
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Set<CharacteristicValue> characteristicsValues = getCharacteristicsValues(sheet.getRow(i), characteristics);

            // exclude empty rows
            if (!sheet.getRow(i).getCell(0).getStringCellValue().equals("")) {
                classifiableTexts.add(characteristicFactory.newClassifiableText(sheet.getRow(i).getCell(0).getStringCellValue(), characteristicsValues));
            }
        }

        //todo: now it's error prone approach. Value's order and parent should be filled somewhere ese.
        for (Characteristic characteristic : characteristics) {
            int i = 1;
            for (CharacteristicValue characteristicValue : characteristic.getPossibleValues()) {
                characteristicValue.setOrderNumber(i++);
            }
        }

        return classifiableTexts;
    }

    private Set<CharacteristicValue> getCharacteristicsValues(Row row, List<Characteristic> characteristics) {
        Set<CharacteristicValue> characteristicsValues = new HashSet<>();

        for (int i = 1; i < row.getLastCellNum(); i++) {
            Characteristic characteristic = characteristics.get(i - 1);
            String valueName = row.getCell(i).getStringCellValue();

            CharacteristicValue value = CharacteristicUtils.findByValue(
                    characteristic.getPossibleValues(), valueName, (val) -> characteristicFactory.newCharacteristicValue(val, 0, characteristic));
            if (value == null) {
                value = characteristicFactory.newCharacteristicValue(valueName, 0, characteristic);
            }
            characteristic.addPossibleValue(value);
            characteristicsValues.add(value);
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

}