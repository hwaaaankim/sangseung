$(function(){
	if(!searchType || searchType === 'name' || searchType === 'phone' || searchType == 'none'){
		$('#periodSearch').hide();
		$('#contactSearchCheck').hide();
		$('#signSearch').hide();
		$('#subjectSearch').hide();
	}else if(searchType==='period'){
		$('#contactSearchCheck').hide();
		$('#signSearch').hide();
		$('#textSearch').hide();
		$('#subjectSearch').hide();
	}else if(searchType === 'contactCheck'){
		$('#periodSearch').hide();
		$('#signSearch').hide();
		$('#textSearch').hide();
		$('#subjectSearch').hide();
	}else if(searchType === 'sign'){
		$('#periodSearch').hide();
		$('#contactSearchCheck').hide();
		$('#textSearch').hide();
		$('#subjectSearch').hide();
	}
	else if(searchType === 'subject'){
		$('#periodSearch').hide();
		$('#contactSearchCheck').hide();
		$('#textSearch').hide();
		$('#signSearch').hide();
	}
	$('#searchType').on('change',function(){
		if($('#searchType option:selected').attr('id')==='searchName' || 
		$('#searchType option:selected').attr('id') === 'searchPhone' ||
		$('#searchType option:selected').attr('id') === 'searchBasic'){
			$('#periodSearch').hide();
			$('#contactSearchCheck').hide();
			$('#subjectSearch').hide();
			$('#signSearch').hide();
			$('#textSearch').show();
			$('#textSearch input').val('');
		}else if($('#searchType option:selected').attr('id')==='searchContact'){
			$('#periodSearch').hide();
			$('#contactSearchCheck').show();
			$('#subjectSearch').hide();
			$('#signSearch').hide();
			$('#textSearch').hide();
		}else if($('#searchType option:selected').attr('id')==='searchSign'){
			$('#periodSearch').hide();
			$('#contactSearchCheck').hide();
			$('#subjectSearch').hide();
			$('#signSearch').show();
			$('#textSearch').hide();
		}else if($('#searchType option:selected').attr('id')==='searchSubject'){
			$('#periodSearch').hide();
			$('#contactSearchCheck').hide();
			$('#subjectSearch').show();
			$('#signSearch').hide();
			$('#textSearch').hide();
		}else if($('#searchType option:selected').attr('id')==='searchPeriod'){
			$('#contactSearchCheck').hide();
			$('#subjectSearch').hide();
			$('#signSearch').hide();
			$('#textSearch').hide();
			$('#periodSearch').show();
			$('#startDate').val(new Date().toISOString().slice(0, 10));
			$('#endDate').val(new Date().toISOString().slice(0, 10));
		}
	});
	
});