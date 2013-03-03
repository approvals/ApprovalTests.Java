#foreach($key in $colors.keySet())

 #foreach($color in $commons.asArray($colors.get($key)))
   
  /// <summary>
		/// This is the same as "$color.get().getFirst()"
		/// </summary>
		public static Primitive ${key}_$StringUtils.padNumber($color.getIndex(1),2)_$color.get().getFirst()
		{
			get { return new Primitive("$color.get().getFirst()"); }
		}
 #end
  #end