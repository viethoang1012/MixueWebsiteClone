document.addEventListener('DOMContentLoaded', function() {
    const slides = document.querySelectorAll('.slide');
    const prevBtn = document.querySelector('.prev-btn');
    const nextBtn = document.querySelector('.next-btn');
    let currentSlide = 0;
    let isAnimating = false;
    
    // Hiển thị slide đầu tiên
    slides[0].classList.add('active');
    
    // Xử lý poster quảng cáo
    const adOverlay = document.getElementById('adOverlay');
    const closeAdBtn = document.getElementById('closeAd');
    
    // Hiển thị poster quảng cáo khi trang được tải
    setTimeout(() => {
        adOverlay.classList.add('active');
    }, 1000); // Hiển thị sau 1 giây để trang tải xong
    
    // Đóng poster quảng cáo khi nhấp vào nút đóng
    closeAdBtn.addEventListener('click', function() {
        adOverlay.classList.remove('active');
        
        // Lưu trạng thái đã đóng vào localStorage để không hiển thị lại trong phiên này
        localStorage.setItem('adClosed', 'true');
    });
    
    // Kiểm tra xem poster đã được đóng trong phiên này chưa
    if (localStorage.getItem('adClosed') === 'true') {
        adOverlay.classList.remove('active');
    }
    
    function nextSlide() {
        if (isAnimating) return;
        isAnimating = true;
        
        // Lưu slide hiện tại
        const currentElement = slides[currentSlide];
        
        // Tính toán slide tiếp theo
        currentSlide = (currentSlide + 1) % slides.length;
        const nextElement = slides[currentSlide];
        
        // Thiết lập vị trí ban đầu cho slide tiếp theo
        nextElement.style.transform = 'translateX(100%)';
        nextElement.classList.add('active');
        
        // Force reflow
        nextElement.offsetHeight;
        
        // Thực hiện animation
        currentElement.style.transform = 'translateX(-100%)';
        currentElement.style.transition = 'transform 0.5s ease-in-out';
        nextElement.style.transform = 'translateX(0)';
        nextElement.style.transition = 'transform 0.5s ease-in-out';
        
        // Xử lý sau khi animation hoàn thành
        setTimeout(() => {
            currentElement.classList.remove('active');
            currentElement.style.transition = '';
            currentElement.style.transform = '';
            nextElement.style.transition = '';
            isAnimating = false;
        }, 500);
    }
    
    function prevSlide() {
        if (isAnimating) return;
        isAnimating = true;
        
        // Lưu slide hiện tại
        const currentElement = slides[currentSlide];
        
        // Tính toán slide trước đó
        currentSlide = (currentSlide - 1 + slides.length) % slides.length;
        const prevElement = slides[currentSlide];
        
        // Thiết lập vị trí ban đầu cho slide trước đó
        prevElement.style.transform = 'translateX(-100%)';
        prevElement.classList.add('active');
        
        // Force reflow
        prevElement.offsetHeight;
        
        // Thực hiện animation
        currentElement.style.transform = 'translateX(100%)';
        currentElement.style.transition = 'transform 0.5s ease-in-out';
        prevElement.style.transform = 'translateX(0)';
        prevElement.style.transition = 'transform 0.5s ease-in-out';
        
        // Xử lý sau khi animation hoàn thành
        setTimeout(() => {
            currentElement.classList.remove('active');
            currentElement.style.transition = '';
            currentElement.style.transform = '';
            prevElement.style.transition = '';
            isAnimating = false;
        }, 500);
    }
    
    // Thêm sự kiện click cho các nút
    nextBtn.addEventListener('click', nextSlide);
    prevBtn.addEventListener('click', prevSlide);
    
    // Tự động chuyển slide sau mỗi 4 giây
    setInterval(nextSlide, 4000);
});


