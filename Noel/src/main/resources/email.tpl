<html>
<head>
  <title>Welcome!</title>
</head>
<body>
  <p>Souhaits:
  <ul>
  <#list wishes as wish>
    <li>${wish.dest}</li>
  </#list>
  </ul>
</body>
</html>  