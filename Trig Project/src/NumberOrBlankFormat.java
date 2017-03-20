import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParsePosition;

public class NumberOrBlankFormat extends Format {

    private static final long serialVersionUID = 1L;
    
    static final NumberOrBlankFormat instance = new NumberOrBlankFormat();
    
   static NumberOrBlankFormat getInstance() {
        return instance;
    }
    NumberFormat numberFormat = DecimalFormat.getNumberInstance();
    
    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        if (obj == null)
            return toAppendTo;
        return numberFormat.format(obj, toAppendTo, pos);
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
        if (source.isEmpty())
            return Double.NaN;
        return numberFormat.parseObject(source, pos);
    }

}
