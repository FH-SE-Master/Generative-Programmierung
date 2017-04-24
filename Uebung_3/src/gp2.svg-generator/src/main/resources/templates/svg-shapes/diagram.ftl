<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE svg>
<svg xmlns="http://www.w3.org/2000/svg"
     width="${width}"
     height="${height}"
     <#if coordinate??>viewBox="${coordinate.x} ${coordinate.y} ${coordinateWidth} ${coordinateHeight}"</#if>
     preserveAspectRatio="none">

    <#-- Need to find out how to calculate the coordiante system -->
    <#if coordinate??>
        <line x1="${(coordinate.coordinateWidth / 2) * -1}" y1="0" x2="${coordinate.coordinateWidth / 2}" y2="0" stroke="#555" stroke-width="0.01"/>
        <line x1="0" y1="${(coordinate.coordinateHeight / 2) * -1}" x2="0" y2="${coordinate.coordinateHeight / 2}" stroke="#555" stroke-width="0.01"/>
    </#if>
<#list shapes as shape>
  ${shape.render()}
</#list>

</svg>