package org.approvaltests.core;

import org.approvaltests.velocity.VelocityApprovals;
import org.junit.jupiter.api.Test;
import org.lambda.query.Query;
import org.lambda.query.Queryable;

import java.lang.reflect.Field;
import java.util.HashMap;

class ApprovalTestPackageSettingsTest
{
  @Test
  void createPackageSettingDocumentation()
  {
    Queryable<Object> fieldTypes = getFieldTypes();
    HashMap<org.packagesettings.Field, String> defaults = new HashMap<>();
    fieldTypes.forEach(t -> defaults.put((org.packagesettings.Field) t, "null"));
    defaults.put(ApprovalTestPackageSettings.FRONTLOADED_REPORTER, "new JunitReporter()");
    defaults.put(ApprovalTestPackageSettings.USE_REPORTER, "new ClipboardReporter()");
    defaults.put(ApprovalTestPackageSettings.APPROVAL_BASE_DIRECTORY, "\"../resources\"");
    defaults.put(ApprovalTestPackageSettings.USE_APPROVAL_SUBDIRECTORY, "\"approvals\"");
    VelocityApprovals.verify(c -> {
      c.put("fields", fieldTypes.toArray());
      c.put("defaults", defaults);
    });
  }

  private Queryable<Object> getFieldTypes()
  {
    Field[] fields = ApprovalTestPackageSettings.class.getFields();
    Queryable<Object> fieldTypes = Query.where(fields, f -> f.getType().equals(org.packagesettings.Field.class))
        .select(f -> {
          try
          {
            return f.get(null);
          }
          catch (IllegalAccessException e)
          {
            throw new RuntimeException(e);
          }
        });
    return fieldTypes;
  }
}
