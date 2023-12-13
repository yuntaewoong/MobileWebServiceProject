from .forms import PostForm
from django.shortcuts import render, get_object_or_404
from django.utils import timezone
from .models import Post
from django.shortcuts import redirect
from rest_framework import viewsets
from .serializers import PostSerializer
from django.http import HttpResponse, JsonResponse

class IntruderImage(viewsets.ModelViewSet):
    queryset = Post.objects.all()
    serializer_class = PostSerializer


def get_SleepStart(request):
    queryset = Post.objects.all()
    return JsonResponse(
            {
                'data0' : queryset[queryset.__len__()-2].published_date,
                'data1' : queryset[queryset.__len__()-4].published_date,
                'data2' : queryset[queryset.__len__()-6].published_date,
                'data3' : queryset[queryset.__len__()-8].published_date,
                'data4' : queryset[queryset.__len__()-10].published_date,
            }
        )

def get_SleepEnd(request):
    queryset = Post.objects.all()
    return JsonResponse(
        {
            'data0' : queryset[queryset.__len__()-1].published_date,
            'data1' : queryset[queryset.__len__()-3].published_date,
            'data2' : queryset[queryset.__len__()-5].published_date,
            'data3' : queryset[queryset.__len__()-7].published_date,
            'data4' : queryset[queryset.__len__()-9].published_date
        }
    )






# Create your views here.
def post_list(request):
    posts = Post.objects.filter(published_date__lte=timezone.now()).order_by('published_date')
    return render(request, 'blog/post_list.html', {'posts': posts})


def post_detail(request, pk):
    post = get_object_or_404(Post, pk=pk)
    return render(request, 'blog/post_detail.html', {'post': post})

def post_new(request):
    if request.method == "POST":
        form = PostForm(request.POST)
        if form.is_valid():
            post = form.save(commit=False)
            post.author = request.user
            post.published_date = timezone.now()
            post.save()
            return redirect('post_detail', pk=post.pk)
    else:
        form = PostForm()
    return render(request, 'blog/post_edit.html', {'form': form})

def post_edit(request, pk):
    post = get_object_or_404(Post, pk=pk)
    if request.method == "POST":
        form = PostForm(request.POST, instance=post)
        if form.is_valid():
            post = form.save(commit=False)
            post.author = request.user
            post.published_date = timezone.now()
            post.save()
            return redirect('post_detail', pk=post.pk)
    else:
        form = PostForm(instance=post)
    return render(request, 'blog/post_edit.html', {'form': form})