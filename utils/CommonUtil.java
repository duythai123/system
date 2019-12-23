package capstone_project.av_service.utils;


import java.lang.reflect.Method;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import capstone_project.av_service.constant.BusinessConstant;


public class CommonUtil {

    public static int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

    public static Instant getCurrentServerTime () {
        return ZonedDateTime.now(ZoneId.of("UTC")).toInstant();
    }

    public static boolean equalOrNull(Object source, Object destination, String[] ignoreFunctions) {
        if (source == destination) {
            return true;
        }
        if (source == null || destination == null) {
            return false;
        }
        if (source.getClass() != destination.getClass()) {
            return false;
        }


        Method[] _dst = destination.getClass().getMethods();
        String _getterName;

        // All methods of destination class.
        for (int i = 0; i < _dst.length; i++) {

            // Pick up getter.
            _getterName = _dst[i].getName();
            if (_getterName.startsWith(ComplementUtil.PREFIX_GETTER)) {
//                if (Arrays.asList(CoreConstants.BYPASS_GETTER_AUDIT_ENTITIES).contains(_getterName)) {
//                    continue;
//                }

                // ignore optional method
                if (ignoreFunctions != null) {
                    if (Arrays.asList(ignoreFunctions).contains(_getterName)) {
                        continue;
                    }
                }

                // Looking for getter @ source class.
                Method _getter = ComplementUtil.findMethod(source, ComplementUtil.PREFIX_GETTER + _getterName.substring(ComplementUtil.PREFIX_GETTER.length()));

                if (_getter.getReturnType() == Instant.class) {
                    continue;
                }

                if (null != _getter) {

                    // Check parameter of setter and returnType of getter.
                    if (1 == _dst[i].getParameterTypes().length
                            && _dst[i].getParameterTypes()[0].getName().equals(_getter.getReturnType().getName())) {

//                        execSetter(destination, _dst[i], execGetter(source, _getter));

                        if (ComplementUtil.execGetter(source, _getter) == null) {
                            if (ComplementUtil.execGetter(destination, _dst[i]) != null) {
                                return false;
                            }
                        } else if (!ComplementUtil.execGetter(source, _getter).equals(ComplementUtil.execGetter(destination, _dst[i]))) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }
    
    public static List<String> getCompanyList(int status) {
    	List<String> companyList = new ArrayList<String>();
    	List<Integer> indexSet = new ArrayList<Integer>();
    	int index = 0;
    	while (status > 0) {
    		if (status % 10 == 1) {
    			indexSet.add(index);
    		}
    		index++;
			status = status/10;
    	}
    	for (int i = 0; i < indexSet.size(); i++) {
    		companyList.add(BusinessConstant.Company.COMPANY_LIST[indexSet.get(i)]);
    	}
    	return companyList;
    }
}
