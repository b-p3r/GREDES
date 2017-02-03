 

//--AgentGen BEGIN=_BEGIN
//--AgentGen END

import org.snmp4j.smi.*;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.agent.*;
import org.snmp4j.agent.mo.*;
import org.snmp4j.agent.mo.snmp.*;
import org.snmp4j.agent.mo.snmp.smi.*;
import org.snmp4j.agent.request.*;
import org.snmp4j.log.LogFactory;
import org.snmp4j.log.LogAdapter;
import org.snmp4j.agent.mo.snmp.tc.*;



//--AgentGen BEGIN=_IMPORT
//--AgentGen END

public class UMINHO-GR-MIB 
//--AgentGen BEGIN=_EXTENDS
//--AgentGen END
implements MOGroup 
//--AgentGen BEGIN=_IMPLEMENTS
//--AgentGen END
{

  private static final LogAdapter LOGGER = 
      LogFactory.getLogger(UMINHO-GR-MIB.class);

//--AgentGen BEGIN=_STATIC
//--AgentGen END

  // Factory
  private MOFactory moFactory = 
    DefaultMOFactory.getInstance();

  // Constants 

  /**
   * OID of this MIB module for usage which can be 
   * used for its identification.
   */
  public static final OID oidUminhoGrMib =
    new OID(new int[] { 1,3,6,1,3,99 });

  // Identities
  // Scalars
  public static final OID oidParamR = 
    new OID(new int[] { 1,3,6,1,3,99,3,1,0 });
  public static final OID oidParamN = 
    new OID(new int[] { 1,3,6,1,3,99,3,2,0 });
  public static final OID oidParamD = 
    new OID(new int[] { 1,3,6,1,3,99,3,3,0 });
  public static final OID oidParamAuthReset = 
    new OID(new int[] { 1,3,6,1,3,99,3,4,0 });
  // Tables

  // Notifications

  // Enumerations




  // TextualConventions
  private static final String TC_MODULE_SNMPV2_TC = "SNMPv2-TC";
  private static final String TC_DISPLAYSTRING = "DisplayString";

  // Scalars
  private MOScalar<Integer32> paramR;
  private MOScalar<Integer32> paramN;
  private MOScalar<Integer32> paramD;
  private MOScalar<OctetString> paramAuthReset;

  // Tables
  public static final OID oidRandomTableEntry = 
    new OID(new int[] { 1,3,6,1,3,99,4,1,1 });

  // Index OID definitions
  public static final OID oidIndexRandHexNumber =
    new OID(new int[] { 1,3,6,1,3,99,4,1,1,1 });

  // Column TC definitions for randomTableEntry:
  public static final String tcModuleSNMPv2Tc = "SNMPv2-TC";
  public static final String tcDefDisplayString = "DisplayString";
    
  // Column sub-identifier definitions for randomTableEntry:
  public static final int colIndexRandHexNumber = 1;
  public static final int colRandomHexadecimalNumber = 2;

  // Column index definitions for randomTableEntry:
  public static final int idxIndexRandHexNumber = 0;
  public static final int idxRandomHexadecimalNumber = 1;

  private MOTableSubIndex[] randomTableEntryIndexes;
  private MOTableIndex randomTableEntryIndex;
  
  private MOTable<RandomTableEntryRow,
                  MOColumn,
                  MOTableModel<RandomTableEntryRow>> randomTableEntry;
  private MOTableModel<RandomTableEntryRow> randomTableEntryModel;


//--AgentGen BEGIN=_MEMBERS
//--AgentGen END

  /**
   * Constructs a UMINHO-GR-MIB instance without actually creating its
   * <code>ManagedObject</code> instances. This has to be done in a
   * sub-class constructor or after construction by calling 
   * {@link #createMO(MOFactory moFactory)}. 
   */
  protected UMINHO-GR-MIB() {
//--AgentGen BEGIN=_DEFAULTCONSTRUCTOR
//--AgentGen END
  }

  /**
   * Constructs a UMINHO-GR-MIB instance and actually creates its
   * <code>ManagedObject</code> instances using the supplied 
   * <code>MOFactory</code> (by calling
   * {@link #createMO(MOFactory moFactory)}).
   * @param moFactory
   *    the <code>MOFactory</code> to be used to create the
   *    managed objects for this module.
   */
  public UMINHO-GR-MIB(MOFactory moFactory) {
  	this();
    createMO(moFactory);
//--AgentGen BEGIN=_FACTORYCONSTRUCTOR
//--AgentGen END
  }

//--AgentGen BEGIN=_CONSTRUCTORS
//--AgentGen END

  /**
   * Create the ManagedObjects defined for this MIB module
   * using the specified {@link MOFactory}.
   * @param moFactory
   *    the <code>MOFactory</code> instance to use for object 
   *    creation.
   */
  protected void createMO(MOFactory moFactory) {
    addTCsToFactory(moFactory);
    paramR = 
      moFactory.createScalar(oidParamR,
                             moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_ONLY), 
                             new Integer32());
    paramN = 
      moFactory.createScalar(oidParamN,
                             moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_ONLY), 
                             new Integer32());
    paramD = 
      moFactory.createScalar(oidParamD,
                             moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_ONLY), 
                             new Integer32());
    paramAuthReset = 
      new ParamAuthReset(oidParamAuthReset, 
                         moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_WRITE));
    paramAuthReset.addMOValueValidationListener(new ParamAuthResetValidator());
    createRandomTableEntry(moFactory);
  }

  public MOScalar<Integer32> getParamR() {
    return paramR;
  }
  public MOScalar<Integer32> getParamN() {
    return paramN;
  }
  public MOScalar<Integer32> getParamD() {
    return paramD;
  }
  public MOScalar<OctetString> getParamAuthReset() {
    return paramAuthReset;
  }


  public MOTable<RandomTableEntryRow,MOColumn,MOTableModel<RandomTableEntryRow>> getRandomTableEntry() {
    return randomTableEntry;
  }


  @SuppressWarnings(value={"unchecked"})
  private void createRandomTableEntry(MOFactory moFactory) {
    // Index definition
    randomTableEntryIndexes = 
      new MOTableSubIndex[] {
      moFactory.createSubIndex(oidIndexRandHexNumber, 
                               SMIConstants.SYNTAX_INTEGER, 1, 1)    };

    randomTableEntryIndex = 
      moFactory.createIndex(randomTableEntryIndexes,
                            false,
                            new MOTableIndexValidator() {
      public boolean isValidIndex(OID index) {
        boolean isValidIndex = true;
     //--AgentGen BEGIN=randomTableEntry::isValidIndex
     //--AgentGen END
        return isValidIndex;
      }
    });

    // Columns
    MOColumn[] randomTableEntryColumns = new MOColumn[2];
    randomTableEntryColumns[idxIndexRandHexNumber] = 
      moFactory.createColumn(colIndexRandHexNumber, 
                             SMIConstants.SYNTAX_INTEGER,
                             moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_ONLY));
    randomTableEntryColumns[idxRandomHexadecimalNumber] = 
      new DisplayString(colRandomHexadecimalNumber,
                        moFactory.createAccess(MOAccessImpl.ACCESSIBLE_FOR_READ_WRITE),
                        (OctetString)null);
    ValueConstraint randomHexadecimalNumberVC = new ConstraintsImpl();
    ((ConstraintsImpl)randomHexadecimalNumberVC).add(new Constraint(0L, 255L));
    ((MOMutableColumn)randomTableEntryColumns[idxRandomHexadecimalNumber]).
      addMOValueValidationListener(new ValueConstraintValidator(randomHexadecimalNumberVC));                                  
    ((MOMutableColumn)randomTableEntryColumns[idxRandomHexadecimalNumber]).
      addMOValueValidationListener(new RandomHexadecimalNumberValidator());
    // Table model
    randomTableEntryModel = 
      moFactory.createTableModel(oidRandomTableEntry,
                                 randomTableEntryIndex,
                                 randomTableEntryColumns);
    ((MOMutableTableModel<RandomTableEntryRow>)randomTableEntryModel).setRowFactory(
      new RandomTableEntryRowFactory());
    randomTableEntry = 
      moFactory.createTable(oidRandomTableEntry,
                            randomTableEntryIndex,
                            randomTableEntryColumns,
                            randomTableEntryModel);
  }



  public void registerMOs(MOServer server, OctetString context) 
    throws DuplicateRegistrationException 
  {
    // Scalar Objects
    server.register(this.paramR, context);
    server.register(this.paramN, context);
    server.register(this.paramD, context);
    server.register(this.paramAuthReset, context);
    server.register(this.randomTableEntry, context);
//--AgentGen BEGIN=_registerMOs
//--AgentGen END
  }

  public void unregisterMOs(MOServer server, OctetString context) {
    // Scalar Objects
    server.unregister(this.paramR, context);
    server.unregister(this.paramN, context);
    server.unregister(this.paramD, context);
    server.unregister(this.paramAuthReset, context);
    server.unregister(this.randomTableEntry, context);
//--AgentGen BEGIN=_unregisterMOs
//--AgentGen END
  }

  // Notifications

  // Scalars
  public class ParamAuthReset extends DisplayStringScalar<OctetString> {
    ParamAuthReset(OID oid, MOAccess access) {
      super(oid, access, new OctetString(),
            0, 
            255);
//--AgentGen BEGIN=paramAuthReset
//--AgentGen END
    }

    public int isValueOK(SubRequest request) {
      Variable newValue =
        request.getVariableBinding().getVariable();
      int valueOK = super.isValueOK(request);
      if (valueOK != SnmpConstants.SNMP_ERROR_SUCCESS) {
      	return valueOK;
      }
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 255)))) {
        valueOK = SnmpConstants.SNMP_ERROR_WRONG_LENGTH;
      }
     //--AgentGen BEGIN=paramAuthReset::isValueOK
     //--AgentGen END
      return valueOK; 
    }

    public OctetString getValue() {
     //--AgentGen BEGIN=paramAuthReset::getValue
     //--AgentGen END
      return super.getValue();    
    }

    public int setValue(OctetString newValue) {
     //--AgentGen BEGIN=paramAuthReset::setValue
     //--AgentGen END
      return super.setValue(newValue);    
    }

     //--AgentGen BEGIN=paramAuthReset::_METHODS
     //--AgentGen END

  }


  // Value Validators
  /**
   * The <code>ParamAuthResetValidator</code> implements the value
   * validation for <code>ParamAuthReset</code>.
   */
  static class ParamAuthResetValidator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 255)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=paramAuthReset::validate
     //--AgentGen END
    }
  }

  /**
   * The <code>RandomHexadecimalNumberValidator</code> implements the value
   * validation for <code>RandomHexadecimalNumber</code>.
   */
  static class RandomHexadecimalNumberValidator implements MOValueValidationListener {
    
    public void validate(MOValueValidationEvent validationEvent) {
      Variable newValue = validationEvent.getNewValue();
      OctetString os = (OctetString)newValue;
      if (!(((os.length() >= 0) && (os.length() <= 255)))) {
        validationEvent.setValidationStatus(SnmpConstants.SNMP_ERROR_WRONG_LENGTH);
        return;
      }
     //--AgentGen BEGIN=randomHexadecimalNumber::validate
     //--AgentGen END
    }
  }

  // Rows and Factories

  public class RandomTableEntryRow extends DefaultMOMutableRow2PC {

     //--AgentGen BEGIN=randomTableEntry::RowMembers
     //--AgentGen END

    public RandomTableEntryRow(OID index, Variable[] values) {
      super(index, values);
     //--AgentGen BEGIN=randomTableEntry::RowConstructor
     //--AgentGen END
    }
    
    public Integer32 getIndexRandHexNumber() {
     //--AgentGen BEGIN=randomTableEntry::getIndexRandHexNumber
     //--AgentGen END
      return (Integer32) super.getValue(idxIndexRandHexNumber);
    }  
    
    public void setIndexRandHexNumber(Integer32 newColValue) {
     //--AgentGen BEGIN=randomTableEntry::setIndexRandHexNumber
     //--AgentGen END
      super.setValue(idxIndexRandHexNumber, newColValue);
    }
    
    public OctetString getRandomHexadecimalNumber() {
     //--AgentGen BEGIN=randomTableEntry::getRandomHexadecimalNumber
     //--AgentGen END
      return (OctetString) super.getValue(idxRandomHexadecimalNumber);
    }  
    
    public void setRandomHexadecimalNumber(OctetString newColValue) {
     //--AgentGen BEGIN=randomTableEntry::setRandomHexadecimalNumber
     //--AgentGen END
      super.setValue(idxRandomHexadecimalNumber, newColValue);
    }
    
    public Variable getValue(int column) {
     //--AgentGen BEGIN=randomTableEntry::RowGetValue
     //--AgentGen END
      switch(column) {
        case idxIndexRandHexNumber: 
        	return getIndexRandHexNumber();
        case idxRandomHexadecimalNumber: 
        	return getRandomHexadecimalNumber();
        default:
          return super.getValue(column);
      }
    }
    
    public void setValue(int column, Variable value) {
     //--AgentGen BEGIN=randomTableEntry::RowSetValue
     //--AgentGen END
      switch(column) {
        case idxIndexRandHexNumber: 
        	setIndexRandHexNumber((Integer32)value);
        	break;
        case idxRandomHexadecimalNumber: 
        	setRandomHexadecimalNumber((OctetString)value);
        	break;
        default:
          super.setValue(column, value);
      }
    }

     //--AgentGen BEGIN=randomTableEntry::Row
     //--AgentGen END
  }
  
  class RandomTableEntryRowFactory 
        implements MOTableRowFactory<RandomTableEntryRow>
  {
    public synchronized RandomTableEntryRow createRow(OID index, Variable[] values)
        throws UnsupportedOperationException 
    {
      RandomTableEntryRow row = 
        new RandomTableEntryRow(index, values);
     //--AgentGen BEGIN=randomTableEntry::createRow
     //--AgentGen END
      return row;
    }
    
    public synchronized void freeRow(RandomTableEntryRow row) {
     //--AgentGen BEGIN=randomTableEntry::freeRow
     //--AgentGen END
    }

     //--AgentGen BEGIN=randomTableEntry::RowFactory
     //--AgentGen END
  }


//--AgentGen BEGIN=_METHODS
//--AgentGen END

  // Textual Definitions of MIB module UMINHO-GR-MIB
  protected void addTCsToFactory(MOFactory moFactory) {
  }


//--AgentGen BEGIN=_TC_CLASSES_IMPORTED_MODULES_BEGIN
//--AgentGen END

  // Textual Definitions of other MIB modules
  public void addImportedTCsToFactory(MOFactory moFactory) {
  }


//--AgentGen BEGIN=_TC_CLASSES_IMPORTED_MODULES_END
//--AgentGen END

//--AgentGen BEGIN=_CLASSES
//--AgentGen END

//--AgentGen BEGIN=_END
//--AgentGen END
}


