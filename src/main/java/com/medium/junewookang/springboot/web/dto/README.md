# DTO 관련
웹 요청과 응답에서 데이터를 주고받을 땐 무조건 DTO를 통해야 한다.    
post 하나를 리턴하는 등 아무리 entity와 같다고 해도, entity를 직접 사용해선 안된다.  
크게 request&response에 해당하는 dto를 만들 수 있을텐데, Post를 예로 들면
- 일반적인 게시물 하나를 반환하는 PostsResponseDto 
- 게시물 여러개를 반환하는 PostsListResponseDto
- 게시물 생성 시 POST요청과 함께 보내는 PostsSaveRequestDto
- 게시물 수정 시 PUT요청과 함께 보내는 PostsUpdateRequestDto  
등이 있을 것이다. 
