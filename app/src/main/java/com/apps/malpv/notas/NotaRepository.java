package com.apps.malpv.notas;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.apps.malpv.notas.db.NotaRoomDatabase;
import com.apps.malpv.notas.db.dao.NotaDao;
import com.apps.malpv.notas.db.entity.NotaEntity;

import java.util.List;

public class NotaRepository {
    private NotaDao notaDao;
    private LiveData<List<NotaEntity>> allNotas;
    private LiveData<List<NotaEntity>> allNotasFavoritas;

    public NotaRepository(Application application){
        NotaRoomDatabase db = NotaRoomDatabase.getDatabase(application);
        notaDao = db.notaDao();
        allNotas = notaDao.getAll();
        allNotasFavoritas = notaDao.getAllFavoritas();
    }

    public LiveData<List<NotaEntity>> getAll(){ return allNotas; }

    public LiveData<List<NotaEntity>> getAllFav(){ return allNotasFavoritas; }

    public void inser (NotaEntity nota){
        new insertAsyncTask(notaDao).execute(nota);
    }

    private static class insertAsyncTask extends AsyncTask<NotaEntity, Void, Void>{
        private NotaDao notaDaoAsyncTask;

        insertAsyncTask(NotaDao dao){
            notaDaoAsyncTask = dao;
        }

        @Override
        protected Void doInBackground(NotaEntity... notaEntities) {
            notaDaoAsyncTask.insert(notaEntities[0]);
            return null;
        }
    }
}
