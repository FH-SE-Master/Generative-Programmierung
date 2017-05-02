<text x="${origin.x}"
      y="${origin.y}"
      font-family="${fontFamily}"
      font-size="${fontSize}"
      stroke-width="${strokeWidth}"
      <#if fillColor??>fill="${fillColor}"</#if>
      <#if strokeColor??>stroke="${strokeColor}"</#if>>
${text}
</text>