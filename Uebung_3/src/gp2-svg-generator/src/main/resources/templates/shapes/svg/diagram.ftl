<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE svg>
<svg xmlns="http://www.w3.org/2000/svg"
     width="${width}"
     height="${height}"
     <#if viewBoxEnabled>viewBox="${minX} ${minY} ${maxX - minX} ${maxY - minY}"
     preserveAspectRatio="none"</#if>>

<#if showAxis>
    <line x1="${minX}" x2="${maxX}" y1="${0}" y2="${0}" stroke="#555" stroke-width="0.01" />
    <line x1="${0}" x2="${0}" y1="${minY}" y2="${maxY}" stroke="#555" stroke-width="0.01" />
</#if>

<#list shapes as shape>
  ${shape.render()}
</#list>

</svg>