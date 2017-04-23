<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE svg>
<svg xmlns="http://www.w3.org/2000/svg"
     width="${width}"
     height="${height}"
     <#-- viewBox="${minX} ${minY} ${maxX - minX} ${maxY - minY}" // Need to check this coordinate system-->
     preserveAspectRatio="none">

<#list shapes as shape>
  ${shape.execute()}
</#list>

</svg>