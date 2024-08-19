package com.lihao.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractHTML {
    public static void main(String[] args) {
        String text = "由于您希望直接获取HTML代码而不是Python脚本，我将使用Plotly的在线编辑器来生成一个3D立方体的HTML表示，该立方体每边对应1英寸，并使用白色虚线显示其尺寸。请注意，生成的HTML将包含对Plotly.js CDN的引用，这是使图形在网页上工作所必需的。\n" +
                "\n" +
                "以下是生成的HTML代码：\n" +
                "\n" +
                "\n" +
                "```html\n" +
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <title>3D Cube with Dimensions</title>\n" +
                "    <script src=\"https://cdn.plot.ly/plotly-2.16.2.min.js\"></script>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div id=\"myDiv\" style=\"width: 600px; height: 400px;\"></div>\n" +
                "    <script>\n" +
                "        var data = [{\n" +
                "            type: 'mesh3d',\n" +
                "            x: [0, 1, 1, 0, 0, 1, 1, 0],\n" +
                "            y: [0, 0, 1, 1, 0, 0, 1, 1],\n" +
                "            z: [0, 0, 0, 0, 1, 1, 1, 1],\n" +
                "            i: [7, 0, 0, 0, 4, 4, 6, 6, 4, 0, 3, 2],\n" +
                "            j: [3, 4, 1, 2, 5, 6, 5, 2, 0, 1, 6, 3],\n" +
                "            k: [0, 7, 2, 3, 6, 7, 1, 1, 5, 5, 7, 6],\n" +
                "            color: '#000000',\n" +
                "            opacity: 1,\n" +
                "            line: {color: '#ffffff', width: 1, dash: 'dot'}\n" +
                "        }];\n" +
                "\n" +
                "        var layout = {\n" +
                "            title: '3D Cube with 1 Inch Dimensions',\n" +
                "            scene: {\n" +
                "                xaxis: {title: 'X'},\n" +
                "                yaxis: {title: 'Y'},\n" +
                "                zaxis: {title: 'Z'},\n" +
                "                aspectmode: 'manual',\n" +
                "                aspectratio: {x: 1, y: 1, z: 1}\n" +
                "            }\n" +
                "        };\n" +
                "\n" +
                "        Plotly.newPlot('myDiv', data, layout);\n" +
                "    </script>\n" +
                "</body>\n" +
                "</html>\n" +
                "```\n" +
                "这段代码创建了一个HTML页面，其中包含一个600x400像素的`<div>`元素，用于显示3D图形。JavaScript部分定义了图形的数据和布局，并使用Plotly.js库将图形渲染到`<div>`元素中。数据部分定义了一个3D网格，表示一个每边为1英寸的立方体，并使用白色虚线显示其边缘。";

        // 正则表达式匹配HTML代码段
        String regex = "<!DOCTYPE html>(.*?)</html>";
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            String htmlCode = matcher.group(0);
            System.out.println("Extracted HTML Code:");
            System.out.println(htmlCode);
        } else {
            System.out.println("No HTML code found.");
        }
    }
}
