<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Задолженность упаковки</title>
    <link rel="stylesheet" href="/js/jquery-ui.min.css">
    <link rel="stylesheet" href="/js/jquery-ui.structure.min.css">
    <link rel="stylesheet" href="/js/jquery-ui.theme.min.css">
    <link rel="stylesheet" href="/css/sap-tv.css">
</head>
<body class="back-color">
<div id="world">
    <div id="table-head" class="table-area">
        <h3 align="center" class="special-header-color">${dateAndInfo}<br>Всего: ${sumQuantity} КГ Осталось: ${sumInProcess} КГ</h3>
        <table align="center">
            <caption class="table-caption-style">
                Данные по поставкам с ${date1} по ${date2}
            </caption>
            <thead id="head-area" class="table-header-color">
            <tr>
                <th>Материал</th>
                <th>Описание</th>
                <#--<th>Дата</th>-->
                <#--<th>Время</th>-->
                <#--<th>ЕИ</th>-->
                <th>Всего, КГ</th>
                <#--<th>Отчековано</th>-->
                <#--<th>Передано на СГП</th>-->
                <th>Осталось, КГ</th>
            </tr>
            </thead>
            <tbody id="data-area">
            <#list resultingList as item>
                <tr>
                    <td class="table-td-alignment">${item.material}</td>
                    <td class="table-td-alignment">${item.description}</td>
                    <#--<td>${item.date}</td>-->
                    <#--<td>${item.time}</td>-->
                    <#--<td>${item.uom}</td>-->
                    <td>${item.quantity}</td>
                    <#--<td>${item.packed}</td>-->
                    <#--<td>${item.transferred}</td>-->
                    <td>${item.inProcess}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</div>
<script src="/js/jquery.js"></script>
<script src="/js/jquery-ui.min.js"></script>
<script src="/js/sap-tv.js"></script>
</body>
</html>