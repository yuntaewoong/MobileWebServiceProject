# Generated by Django 4.2.5 on 2023-10-01 06:24

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('blog', '0002_remove_post_author_post_image'),
    ]

    operations = [
        migrations.AlterField(
            model_name='post',
            name='image',
            field=models.ImageField(default='intruder_image/default_error.png', upload_to='intruder_image/%Y/%m/%d/'),
        ),
    ]
