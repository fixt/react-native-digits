#import "UIColorString.h"

@implementation UIColor (String)

+ (UIColor *)colorWithString:(NSString *)str {
    return [[UIColor alloc] initWithString:str];
}

- (UIColor *)initWithString:(NSString *)str {
    CGFloat red = 0,green = 0,blue = 0,alpha = 1.0;
    if ([str hasPrefix:@"#"]) {
        // #rrggbb or #rrggbbaa
        uint32_t colorValue = 0;
        sscanf(str.UTF8String, "#%x", &colorValue);
        if (str.length < 9) {
            colorValue <<= 8;
            colorValue |= 0xFF;
        }
        red     = ((colorValue & 0xFF000000) >> 24) / 255.0;
        green   = ((colorValue & 0x00FF0000) >> 16) / 255.0;
        blue    = ((colorValue & 0x0000FF00) >>  8) / 255.0;
        alpha   =  (colorValue & 0x000000FF) / 255.0;
    } else if ([str hasPrefix:@"rgb("]) {
        // rgb(1,2,3)
        int r,g,b;
        sscanf(str.UTF8String, "rgb(%d,%d,%d)", &r, &g, &b);
        red = r / 255.0;
        green = g / 255.0;
        blue = b / 255.0;
    } else if ([str hasPrefix:@"rgba("]) {
        // rgba(1,2,3,0.75)
        int r,g,b;
        sscanf(str.UTF8String, "rgba(%d,%d,%d,%g)", &r, &g, &b, &alpha);
        red = r / 255.0;
        green = g / 255.0;
        blue = b / 255.0;
    }
    
    return [self initWithRed:red green:green blue:blue alpha:alpha];
}

@end
