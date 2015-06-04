package com.thefightpredictor.sportsbook.core.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.thefightpredictor.sportsbook.core.data.transferobject.LedgerEntryInput;
import com.thefightpredictor.sportsbook.core.service.interfaces.LedgerService;

@Stateless
public class LedgerServiceImpl implements LedgerService
{
    @PersistenceContext
    private EntityManager em;

    public void uploadFile(final LedgerEntryInput ledgerInput)
    {
        final String fileName = ledgerInput.getFileName() == null ? "Unknown" : ledgerInput.getFileName() ;

        final String completeFilePath = "c:/temp/" + fileName;

        try
        {
            //Save the file
            final File file = new File(completeFilePath);

            if (!file.exists())
            {
                file.createNewFile();
            }

            final FileOutputStream fos = new FileOutputStream(file);

            fos.write(ledgerInput.getFileData());
            fos.flush();
            fos.close();
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
    }
}
