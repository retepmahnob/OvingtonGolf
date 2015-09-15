package com.peter.ovingtongolf.utilities;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.peter.ovingtongolf.databaseProvider.sqlcontractGolf;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.peter.ovingtongolf.utilities.sqlApplyButton.SQL_ADD;
import static com.peter.ovingtongolf.utilities.sqlApplyButton.SQL_APPLY;
import static com.peter.ovingtongolf.utilities.sqlApplyButton.SQL_DELETE;
import static com.peter.ovingtongolf.utilities.sqlApplyButton.SQL_UNDO;

/**
 * Created by peter on 2/09/15.
 */
public class sqlViewModel implements sqlEditText.OnFieldChangedListener, sqlApplyButton.OnSqlActionListener {

    private String currentId;
    private String TableUniqueId;
    private Uri DatabaseURI;
    private ContentResolver Resolver;

    public void SetDBUniqueID(Uri databaseURI, String courseId) {
        TableUniqueId = courseId;
        DatabaseURI = databaseURI;
    }

    public void SetContentResolver(ContentResolver contentResolver) {
        Resolver = contentResolver;
    }

    enum CurrentAction {Adding, Updating};
    private CurrentAction currentState;

    public void OnCurrentRecordChanged(String id){

        currentId = id;

        for (sqlFieldManager field : activeEdits) {
            field.FieldChanged = false;
        }
        for (sqlButtonManager button : activeButtons) {

            if ((button.buttonActions & SQL_ADD) > 0){
                button.sqlButton.SetCurrentAction(sqlApplyButton.sqlButtonAction.baAdd);
            }
            else if ((button.buttonActions & SQL_DELETE) > 0){
                button.sqlButton.SetCurrentAction(sqlApplyButton.sqlButtonAction.baDelete);
            }
        }
        currentState = CurrentAction.Updating;
    }

    private class sqlButtonManager{

        public sqlButtonManager (sqlApplyButton button, int sqlActions){
            buttonActions = sqlActions;
            currentAction = sqlApplyButton.sqlButtonAction.baAdd;
            sqlButton = button;
        }
        public void SetCurrentAction (sqlApplyButton.sqlButtonAction newAction)
        {
            currentAction = newAction;
            sqlButton.SetCurrentAction(currentAction);
        }
        public int buttonActions;
        public sqlApplyButton.sqlButtonAction currentAction;
        sqlApplyButton sqlButton;

    }

    private class sqlFieldManager{
        public sqlFieldManager(sqlEditText editText, String table, String field){

            editField = editText;
            sqlTable = table;
            sqlField = field;

        }
        public boolean FieldChanged = false;
        private String sqlTable;
        private String sqlField;
        public String CurrentValue;
        public sqlEditText editField;
    }
    private List<sqlButtonManager> activeButtons = new ArrayList<sqlButtonManager>();
    private List<sqlFieldManager> activeEdits = new ArrayList<sqlFieldManager>();

    @Override
    public void OnFieldChanged(sqlEditText textField, String currentValue, String originalValue) {

        for (sqlFieldManager field : activeEdits) {
            if (field.editField == textField){

                Log.d(this.getClass().getName(), "table:" + field.sqlTable + " field:" + field.sqlField + " was '" + originalValue + "' is now '" + currentValue +"'");
                if (originalValue.equals(currentValue)){
                    field.FieldChanged = false;
                }
                else {
                    field.FieldChanged = true;
                    field.CurrentValue = currentValue;
                }
                break;

            }
        }
        UpdateButtonState();

    }

    private void UpdateButtonState() {

        for (sqlFieldManager field : activeEdits) {
            if (field.FieldChanged){

                for (sqlButtonManager button : activeButtons) {

                    if ((button.buttonActions & SQL_APPLY) > 0){
                        button.sqlButton.SetCurrentAction(sqlApplyButton.sqlButtonAction.baApply);
                    }
                    else if ((button.buttonActions & SQL_UNDO) > 0){
                        button.sqlButton.SetCurrentAction(sqlApplyButton.sqlButtonAction.baUndo);
                    }

                }
                return;
            }
        }
        for (sqlButtonManager button : activeButtons) {

            if ((button.buttonActions & SQL_ADD) > 0){
                button.sqlButton.SetCurrentAction(sqlApplyButton.sqlButtonAction.baAdd);
            }
            else if ((button.buttonActions & SQL_DELETE) > 0){
                button.sqlButton.SetCurrentAction(sqlApplyButton.sqlButtonAction.baDelete);
            }
        }
    }


    @Override
    public void OnActionRequested(sqlApplyButton button, sqlApplyButton.sqlButtonAction action, boolean isPressed) {

        for (sqlButtonManager buttonManager : activeButtons) {
            if (buttonManager.sqlButton == button){
                Log.d(this.getClass().getName(), " Found  Action " + action + "  ===========>>>>>>>>>>>>>>>>>" + isPressed);
                if (!isPressed) {
                    if(action == sqlApplyButton.sqlButtonAction.baAdd) {
                        currentState = CurrentAction.Adding;
                        for (sqlFieldManager field : activeEdits) {
                            field.editField.setSqlFieldData("");
                        }
                        if (buttonManager.currentAction == sqlApplyButton.sqlButtonAction.baAdd) {
                            buttonManager.SetCurrentAction(sqlApplyButton.sqlButtonAction.baApply);
                        } else if (buttonManager.currentAction == sqlApplyButton.sqlButtonAction.baApply) {
                            buttonManager.SetCurrentAction(sqlApplyButton.sqlButtonAction.baAdd);
                        }
                    }
                    else if(action == sqlApplyButton.sqlButtonAction.baApply) {
                        if (currentState == CurrentAction.Adding){
                            final UUID courseUUId = UUID.randomUUID();

                            ContentValues values = new ContentValues();
                            values.put(TableUniqueId, courseUUId.toString());
                            for (sqlFieldManager field : activeEdits) {
                                values.put(field.sqlField, field.CurrentValue);
                            }
                            Resolver.insert(sqlcontractGolf.Course.buildUri(), values);
                        }

                    }
                    else if(action == sqlApplyButton.sqlButtonAction.baDelete) {
                        if (!TextUtils.isEmpty(currentId)){
                            Resolver.delete(sqlcontractGolf.Course.buildUri(), sqlcontractGolf.Course._ID+"=?", new String[]{currentId});
                        }
                    }
                }
                return;
            }
        }
        Log.d(this.getClass().getName(), "   Action " + action + "  ===========>>>>>>>>>>>>>>>>>" + isPressed);

    }

    public void FindControls (View view){

        List<View> visited = new ArrayList<View>();
        List<View> unvisited = new ArrayList<View>();
        unvisited.add(view);

        while (!unvisited.isEmpty()) {
            View child = unvisited.remove(0);
            visited.add(child);
            if(child instanceof sqlEditText ) {

                sqlEditText testEditor = (sqlEditText)child;
                testEditor.setSqlListener(this);
                StringBuilder sField = new StringBuilder();
                StringBuilder sTable = new StringBuilder();
                testEditor.GetSqlConfiguration (sTable, sField);
                activeEdits.add(new sqlFieldManager(testEditor, sTable.toString(), sField.toString()));

            } else if(child instanceof sqlApplyButton) {

                int buttonFunctions = ((sqlApplyButton) child).setSqlListener(this);
                activeButtons.add(new sqlButtonManager((sqlApplyButton) child, buttonFunctions));

            }
            else if (child instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) child;
                for (int i=0; i<group.getChildCount(); i++)
                    unvisited.add(group.getChildAt(i));
            }
        }
    }

}
