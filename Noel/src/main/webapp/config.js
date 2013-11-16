CKEDITOR.editorConfig = function(config) {
    config.toolbar = 'Toolbar';
    config.resize_enabled = false;
    config.toolbar_Toolbar =
    	[
    	    { name: 'basicstyles', items : [ 'Bold','Italic','Underline','Strike' ] },
    	    { name: 'paragraph',   items : [ 'PasteFromWord', 'NumberedList','BulletedList','-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock' ] },
    	    { name: 'styles',      items : [ 'Format','Font','FontSize' ] },
    	    { name: 'colors',      items : [ 'TextColor', 'Image', 'Smiley', 'Link' ] }
    	];
};